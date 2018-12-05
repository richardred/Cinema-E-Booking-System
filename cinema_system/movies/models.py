from django.db import models

# Create your models here.

class MovieInfo(models.Model):
    title = models.CharField(max_length=50, blank=True, default='', primary_key=True)
    releaseDate = models.CharField(max_length=50, blank=True, default='')
    director = models.CharField(max_length=50, blank=True, default='')
    summary = models.CharField(max_length=550, blank=True, default='')
    poster = models.CharField(max_length=50, blank=True, default='')
    rating = models.CharField(max_length=10, blank=True, default='')
    modelType = models.CharField(max_length=20, blank=True, default='')

