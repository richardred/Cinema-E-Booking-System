from django.contrib import admin
from django.urls import path, include

urlpatterns = (
    path('', include('userAccount.urls')),
    path('admin/', admin.site.urls),
    path('accounts/', include('userAccount.urls')),
    path('accounts/', include('django.contrib.auth.urls')),
    path('movies/', include('movies.urls')),
    path('movies/', include('paymentSystem.urls')),

)
