from django import forms

QUANTITY = (('0', '0'), ('1', '1'), ('2', '2'), ('3', '3'), ('4', '4'),
            ('5', '5'), ('6', '6'), ('7', '7'), ('8', '8'), ('9', '9'))


class adultForm(forms.Form):
    adultQuantity = forms.ChoiceField(choices=QUANTITY, label='Quantity', widget=forms.Select(), required=False)

class childForm(forms.Form):
    childQuantity = forms.ChoiceField(choices=QUANTITY, label='Quantity', widget=forms.Select(), required=False)

class seniorForm(forms.Form):
    seniorQuantity = forms.ChoiceField(choices=QUANTITY, label='Quantity', widget=forms.Select(), required=False)
