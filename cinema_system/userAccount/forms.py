from django import forms
from django.contrib.auth.forms import UserCreationForm, UserChangeForm
from .models import UserInfo
from paymentSystem.models import PaymentInfo

STATES = (('', '----'), ('AL', 'AL'), ('AK', 'AK'), ('AZ', 'AZ'), ('AR', 'AR'))

class UserLogin(forms.ModelForm):

    email = forms.EmailField(required=True)
    password = forms.CharField(widget=forms.PasswordInput())

    class Meta:
        model = UserInfo
        fields = ['email', 'password']

class UserRegister(UserCreationForm):
    class Meta:
        model = UserInfo
        fields = ['first_name', 'last_name', 'email', 'password1', 'password2', 'street_address',
                'city', 'state', 'zipcode', 'sendPromo']
    first_name = forms.CharField(label='First Name', required=True)
    last_name = forms.CharField(label='Last Name', required=True)

    street_address = forms.CharField(label='Street TEST Address', required=False)
    city = forms.CharField(label='City', required=False)
    state = forms.ChoiceField(choices=STATES, label='State', widget=forms.Select(), required=False)
    zipcode = forms.CharField(label = 'Zip Code', required=False)
    sendPromo = forms.BooleanField(label='Sign up to receive Promotions!', required=False)

    def clean_email(self):
        email = self.cleaned_data.get('email')
        try:
            match = UserInfo.objects.get(email=email)
            print(match)
        except UserInfo.DoesNotExist:
            return email

        raise forms.ValidationError('This email address is already in use.')

    def getPromo(self):
        return self.cleaned_data.get('sendPromo')

    def getFirstName(self):
        return self.cleaned_data.get('first_name')

    def getLastName(self):
        return self.cleaned_data.get('last_name')

    def check_passwords(self):
        password1 = self.cleaned_data.get('password1')
        password2 = self.cleaned_data.get('password2')

        if password1 != password2:
            raise forms.ValidationError('Passwords do not match.')
        return password2

class EditProfileForm(UserChangeForm):
    class Meta:
        password = None
        model = UserInfo
        fields = ['first_name', 'last_name', 'email', 'street_address',
                  'city', 'state', 'zipcode', 'sendPromo']

class EditPaymentInfo(forms.ModelForm):
    class Meta:
        model = PaymentInfo
        fields = ['cardNumber', 'cardMonth', 'cardYear', 'cardPin', 'cardFirstName', 'cardLastName']
