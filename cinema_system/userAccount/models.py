from django.db import models
from uuid import uuid4
from django.contrib.auth.models import AbstractUser, BaseUserManager
from django.utils import timezone

STATES = (('', '---'), ('AL', 'AL'), ('AK', 'AK'), ('AZ', 'AZ'), ('AR', 'AR'))

class CustomUserManager(BaseUserManager):

    def _create_user(self, email, password, is_staff, is_superuser, **extra_fields):
        now = timezone.now()
        if not email:
            raise ValueError('email must be set')
        email = self.normalize_email(email)
        user = UserInfo(email = email, is_staff=is_staff,
                        is_superuser=is_superuser, date_joined=now, is_active=False
                        **extra_fields)
        user.set_password(password)
        user.save()
        return user

    def create_user(self, email, password, **extra_fields):
        return self._create_user(email, password, False, False, **extra_fields)

    def create_superuser(self, email, password, **extra_fields):
        return self._create_user(email, password, True, True, **extra_fields)

def generateUUID():
    return str(uuid4())

class UserInfo(AbstractUser):

    username = None
    email = models.EmailField(unique=True)

    objects = CustomUserManager()

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = []
    id = models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')
    email_confirmed = models.BooleanField(default=False)
    activation_code = models.CharField(max_length=50, blank=True, default='')
    street_address = models.CharField(max_length=100, blank=True, default='')
    city = models.CharField(max_length=50, blank=True, default='')
    state = models.CharField(max_length=2, choices=STATES, blank=True, default='')
    zipcode = models.CharField(max_length=50, blank=True, default='')
    sendPromo = models.BooleanField(default=False)
    accountType = models.CharField(max_length=50, default='Web User')
