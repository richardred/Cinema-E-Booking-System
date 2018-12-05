from django.shortcuts import render, redirect
from .forms import UserRegister, EditProfileForm, EditPaymentInfo
from django.contrib.auth.forms import PasswordChangeForm
from django.core.mail import send_mail
from django.contrib.auth import update_session_auth_hash
import string, random
from .models import UserInfo
from movies.models import MovieInfo
from paymentSystem.models import Order

# Create your views here.


def homepage(request):
    movieObject = MovieInfo.objects.all()
    return render(request, 'homepage/index.html', {'movies': movieObject})

def registerRequest(request):
    if request.method == 'POST':
        user = UserRegister(request.POST)
        if user.is_valid():
            email = user.clean_email()
            firstname = user.getFirstName()
            lastname = user.getLastName()
            user.save()
            code = codeGenerator(email)
            sendEmail(request, email, firstname, lastname, code)
            request.session['email'] = email
            if user.getPromo():
                sendPromo(request, email, firstname, lastname)
            return redirect('http://127.0.0.1:8000/accounts/activation/')
    else:
        user = UserRegister()
    return render(request, 'registration/register.html', {'register': user})

def sendPromo(request, email, firstname, lastname):
    subject = 'First Purchase Promotion Code!'
    body = 'Thank you, ' + firstname + ' ' + lastname + ". We appreciate you joining our mailing list!. \nPlease use this code PurchaseOne to get 10% off your first purchase!!"
    botEmail = 'sunnybot94@gmail.com'
    send_mail(subject, body, botEmail, [email], fail_silently=False)

def sendEmail(request, email, firstname, lastname, code):
    user = UserRegister(request.POST)
    subject = 'Hello from Cinema E-Booking System!'
    body = 'Welcome, ' + firstname + ' ' + lastname + ". We have the best online ticketing system in the world. \nPlease use this code " + code + " to finish activating your account!"
    botEmail = 'sunnybot94@gmail.com'
    send_mail(subject, body, botEmail, [email], fail_silently=False)
    return render(request, 'registration/register_email_sent.html', {'user': user})

def codeGenerator(email):
    code = ''.join(random.SystemRandom().choice(string.ascii_uppercase + string.digits) for _ in range(6))
    user = UserInfo.objects.get(email=email)
    user.activation_code = code
    user.save()
    return code

def activateAccount(request):
    email = request.session['email']
    user = UserInfo.objects.get(email=email)
    code = request.POST.get('code')
    if request.method == 'POST':
        if(user.activation_code == code):

            user.email_confirmed = True
            user.active = True;
            user.save()
            return render(request, 'registration/account_activation_confirmed.html')
        else:
            return render(request, 'registration/register_email_sent.html')
    return render(request, 'registration/register_email_sent.html', {'user': user})

def viewProfile(request):
    user = request.user
    return render(request, 'profile/view_profile.html', {'user': user})

def editProfile(request):
    if request.method == 'POST':
        form = EditProfileForm(request.POST, instance=request.user)

        if form.is_valid():
            form.save()
            return redirect('/accounts/viewProfile/')
    else:
        form = EditProfileForm(instance=request.user)
        return render(request, 'profile/edit_profile.html', {'form': form})

def editPayment(request):
    if request.method == 'POST':
        form = EditPaymentInfo(request.POST, instance=request.user)

        if form.is_valid():
            form.save()
            return redirect('/accounts/viewProfile/')
    else:
        form = EditPaymentInfo(instance=request.user)
        return render(request, 'profile/edit_profile.html', {'form': form})

def changePassword(request):
    if request.method == 'POST':
        form = PasswordChangeForm(data=request.POST, user=request.user)

        if form.is_valid():
            form.save()
            update_session_auth_hash(request, form.user)
            return redirect('/accounts/viewProfile/')
    else:
        form = PasswordChangeForm(user=request.user)
        return render(request, 'profile/change_password.html', {'form': form})

def orderHistory(request):
    user = UserInfo(request.POST)
    orderHistory = Order(user)
    print(orderHistory)
    return render(request, 'profile/order_history.html')
