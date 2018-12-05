from django.db import models
import uuid

from userAccount.models import UserInfo

# Create your models here.

class PaymentInfo(models.Model):

    userAccount = models.OneToOneField(UserInfo,
                                       on_delete=models.CASCADE,
                                       primary_key=True)

    cardNumber = models.CharField(max_length=50, blank=True, default='0000')
    cardMonth = models.CharField(max_length=50, blank=True, default='0000')
    cardYear = models.CharField(max_length=50, blank=True, default='0000')
    cardPin = models.CharField(max_length=50, blank=True, default='0000')
    cardFirstName = models.CharField(max_length=50, blank=True, default='')
    cardLastName = models.CharField(max_length=50, blank=True, default='')

class PromoCode(models.Model):

    promotionCode = models.CharField(max_length=50, blank=True, default='', primary_key=True)
    discountPercentage = models.CharField(max_length=25, blank=True, default='00')

class Ticket(models.Model):
    ticket_type = models.CharField(max_length=50, blank=True, default='', primary_key=True)
    ticket_price = models.IntegerField()

class Order(models.Model):

    userAccount = models.OneToOneField(UserInfo,
                                       on_delete=models.CASCADE,
                                       primary_key=True, default='0')
    date = models.CharField(max_length=50, blank=True, default='')
    time = models.CharField(max_length=50, blank=True, default='')
    title = models.CharField(max_length=50, blank=True, default='')
    adultTicket = models.CharField(max_length=50, blank=True, default='')
    childTicket = models.CharField(max_length=50, blank=True, default='')
    seniorTicket = models.CharField(max_length=50, blank=True, default='')
    totalSum = models.CharField(max_length=50, blank=True, default='')


