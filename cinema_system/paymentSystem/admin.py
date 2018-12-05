from django.contrib import admin
from paymentSystem.models import PromoCode, PaymentInfo, Ticket, Order

# Register your models here.
admin.site.register(PromoCode)
admin.site.register(PaymentInfo)
admin.site.register(Ticket)
admin.site.register(Order)
