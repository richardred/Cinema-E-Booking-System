from django.shortcuts import render
from movies.models import MovieInfo
from .models import Ticket, PromoCode, Order
from .forms import adultForm, childForm, seniorForm
from django.http import HttpResponseRedirect
from django.contrib.auth.decorators import login_required
from userAccount.forms import EditPaymentInfo
import string, random


def selectTime(request, title):
    movieObject = MovieInfo.objects.get(title=title)
    return render(request, 'selections/showtimes.html', {'movie': movieObject})

def selectTickets(request, title, date, time):
    if request.method == 'POST':

        adult = request.POST.get('adultQuantity', 0)
        print(adult)
        child = request.POST.get('childQuantity', 0)
        print(child)
        senior = request.POST.get('seniorQuantity', 0)
        print(senior)
        adultTotal = int(adult) * 15
        childTotal = int(child) * 10
        seniorTotal = int(senior) * 10
        totalTickets = adultTotal + childTotal + seniorTotal
        salesTax = round((totalTickets * (7/100)), 3)
        totalCost =  salesTax + totalTickets

        movieObject = MovieInfo.objects.get(title=title)
        adultTicket = Ticket.objects.get(ticket_type='Adult')
        childTicket = Ticket.objects.get(ticket_type='Child')
        seniorTicket = Ticket.objects.get(ticket_type='Senior')

        aForm = adultForm()
        cForm = childForm()
        sForm = seniorForm()

        args = {
            'adult': adult,
            'child': child,
            'senior': senior,
            'adultTotal': adultTotal,
            'childTotal': childTotal,
            'seniorTotal': seniorTotal,
            'totalTickets': totalTickets,
            'salesTax': salesTax,
            'totalCost': totalCost,
            'adultForm': aForm,
            'childForm': cForm,
            'seniorForm': sForm,
            'movie': movieObject,
            'date': date,
            'time': time,
            'adultTicket': adultTicket,
            'childTicket': childTicket,
            'seniorTicket': seniorTicket,
        }

        #return HttpResponseRedirect('http://127.0.0.1:8000/movies/book/' + title + '/' + date + '/' + time, args)
        return render(request, 'selections/tickets_total.html', args)
    else:
        movieObject = MovieInfo.objects.get(title=title)
        adultTicket = Ticket.objects.get(ticket_type='Adult')
        childTicket = Ticket.objects.get(ticket_type='Child')
        seniorTicket = Ticket.objects.get(ticket_type='Senior')

        adult = adultForm()
        child = childForm()
        senior = seniorForm()

        args = {'movie': movieObject,
                'date': date,
                'time': time,
                'adultTicket': adultTicket,
                'childTicket': childTicket,
                'seniorTicket': seniorTicket,
                'adultForm': adult,
                'childForm': child,
                'seniorForm': senior
                }

        return render(request, 'selections/tickets_total.html', args)

def applyPromo(request, title, date, time, adult, child, senior):
    if request.method == 'POST':
        promoCode = request.POST.get("promo", '')
        promoExists = PromoCode.objects.filter(promotionCode__exact=promoCode)
        if promoExists:
            promoExists = PromoCode.objects.get(promotionCode__exact=promoCode)
            promoPercent = promoExists.discountPercentage
            adultTotal = adult * 15
            childTotal = child * 10
            seniorTotal = senior * 10
            totalTicket = adultTotal + childTotal + seniorTotal
            salesTax = round((totalTicket * (7/100)), 2)
            totalCost = totalTicket + salesTax
            discountedRate = totalCost * promoPercent/100
            adjustedCost = totalCost - discountedRate

            movieObject = MovieInfo.objects.get(title=title)
            adultTicket = Ticket.objects.get(ticket_type='Adult')
            childTicket = Ticket.objects.get(ticket_type='Child')
            seniorTicket = Ticket.objects.get(ticket_type='Senior')

            aForm = adultForm()
            cForm = childForm()
            sForm = seniorForm()

            args = {
            'adult': adult,
            'child': child,
            'senior': senior,
            'adultTotal': adultTotal,
            'childTotal': childTotal,
            'seniorTotal': seniorTotal,
            'totalTickets': totalTicket,
            'salesTax': salesTax,
            'totalCost': totalCost,
            'adultForm': aForm,
            'childForm': cForm,
            'seniorForm': sForm,
            'movie': movieObject,
            'date': date,
            'time': time,
            'adultTicket': adultTicket,
            'childTicket': childTicket,
            'seniorTicket': seniorTicket,
            'promoPercent': promoPercent,
            'discountedRate': discountedRate,
            'adjustedCost': adjustedCost,
            }

            return render(request, 'selections/added_promo.html', args)
        else:

            adultTotal = int(adult) * 15
            childTotal = int(child) * 10
            seniorTotal = int(senior) * 10
            totalTickets = adultTotal + childTotal + seniorTotal
            salesTax = round((totalTickets * (7/100)), 3)
            totalCost =  salesTax + totalTickets

            movieObject = MovieInfo.objects.get(title=title)
            adultTicket = Ticket.objects.get(ticket_type='Adult')
            childTicket = Ticket.objects.get(ticket_type='Child')
            seniorTicket = Ticket.objects.get(ticket_type='Senior')

            aForm = adultForm()
            cForm = childForm()
            sForm = seniorForm()
            invalid = 'Invalid Promo Code.'
            args = {
                'adult': adult,
                'child': child,
                'senior': senior,
                'adultTotal': adultTotal,
                'childTotal': childTotal,
                'seniorTotal': seniorTotal,
                'totalTickets': totalTickets,
                'salesTax': salesTax,
                'totalCost': totalCost,
                'adultForm': aForm,
                'childForm': cForm,
                'seniorForm': sForm,
                'movie': movieObject,
                'date': date,
                'time': time,
                'adultTicket': adultTicket,
                'childTicket': childTicket,
                'seniorTicket': seniorTicket,
                'invalid': invalid
            }
            #return render(request, 'selections/added_promo.html', args)
            return HttpResponseRedirect('http://127.0.0.1:8000/movies/book/'+title+'/'+date+'/'+time, args)

@login_required
def checkout(request, title, date, time, adult, child, senior):
    paymentForm = EditPaymentInfo()
    adultTotal = adult * 15
    childTotal = child * 10
    seniorTotal = senior * 10
    totalTicket = adultTotal + childTotal + seniorTotal
    salesTax = round((totalTicket * (7/100)), 2)
    totalCost = totalTicket + salesTax
    args = {
        'adult': adult,
        'child': child,
        'senior': senior,
        'adultTotal': adultTotal,
        'childTotal': childTotal,
        'seniorTotal': seniorTotal,
        'totalTickets': totalTicket,
        'salesTax': salesTax,
        'totalCost': totalCost,
        'date': date,
        'time': time,
        'title': title,
        'paymentForm': paymentForm,
    }
    return render(request, 'checkout/checkout.html', args)


@login_required
def checkout_promo(request, title, date, time, adult, child, senior, discount):
    paymentForm = EditPaymentInfo()
    adultTotal = int(adult) * 15
    childTotal = int(child) * 10
    seniorTotal = int(senior) * 10
    totalTicket = int(adultTotal + childTotal + seniorTotal)
    salesTax = round((totalTicket * (7/100)), 2)
    totalCost = totalTicket + salesTax
    discountedRate = float(discount)
    adjustedCost = totalCost - discountedRate

    args = {
        'adult': adult,
        'child': child,
        'senior': senior,
        'adultTotal': adultTotal,
        'childTotal': childTotal,
        'seniorTotal': seniorTotal,
        'totalTickets': totalTicket,
        'salesTax': salesTax,
        'totalCost': totalCost,
        'date': date,
        'time': time,
        'title': title,
        'discountedRate': discountedRate,
        'adjustedCost': adjustedCost,
        'paymentForm': paymentForm,
    }
    return render(request, 'checkout/checkout_with_promo.html', args)

def codeGenerator():
    code = random.sample(range(100000), 1)
    code = string(code)
    print(code)
    return code

def confirm(request, title, date, time, adult, child, senior, sum):
    '''code = codeGenerator()
    confirmed_Order = Order.objects.create(pk=code)
    confirmed_Order.adultTicket = adult
    confirmed_Order.childTicket = child
    confirmed_Order.seniorTicket = senior
    confirmed_Order.date = date
    confirmed_Order.time = time
    confirmed_Order.totalSum = sum
    confirmed_Order.user = request.user
    confirmed_Order.title = title
    confirmed_Order.save()
    print("CHECKING OUT")'''
    return render(request, 'checkout/checkout_confirmation.html')