from django.contrib import admin

# Register your models here.
from userAccount.models import UserInfo

admin.site.register(UserInfo)