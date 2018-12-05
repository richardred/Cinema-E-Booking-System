from django.urls import path
from . import views

urlpatterns = [
    path('book/<str:title>', views.selectTime, name="selectTime"),
    path('book/<str:title>/<str:date>/<str:time>', views.selectTickets, name="selectTickets"),
    path('book/<str:title>/<str:date>/<str:time>/<int:adult>/<int:child>/<int:senior>/checkout', views.checkout, name="checkout"),
    path('book/<str:title>/<str:date>/<str:time>/applypromo/<int:adult>/<int:child>/<int:senior>', views.applyPromo, name="applyPromo"),
    path('book/<str:title>/<str:date>/<str:time>/applypromo/<str:adult>/<str:child>/<str:senior>/<str:discount>/checkout', views.checkout_promo, name="checkout_promo"),
    path('book/<str:title>/<str:date>/<str:time>/<int:adult>/<int:child>/<int:senior>/checkout/confirmation/<str:sum>', views.confirm, name="confirm"),

]