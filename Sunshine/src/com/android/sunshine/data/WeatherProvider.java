package com.android.sunshine.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class WeatherProvider extends ContentProvider {

	private static final int WEATHER = 100;
	private static final int WEATHER_WITH_LOCATION = 101;
	private static final int WEATHER_WITH_LOCATION_AND_DATE = 102;
	private static final int LOCATION = 300;
	private static final int LOCATION_ID = 301;
	
	private WeatherDbHelper mOpenHelper;
	
	private static final UriMatcher sUriMatcher = buildUriMatcher();
	
    private static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.
 
        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = WeatherContract.CONTENT_AUTHORITY;
 
        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, WeatherContract.PATH_WEATHER, WEATHER);
        matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*", WEATHER_WITH_LOCATION);
        matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*/*", WEATHER_WITH_LOCATION_AND_DATE);
 
        matcher.addURI(authority, WeatherContract.PATH_LOCATION, LOCATION);
        matcher.addURI(authority, WeatherContract.PATH_LOCATION + "/#", LOCATION_ID);
 
        return matcher;
    }
	
	@Override
	public boolean onCreate() {
		
		mOpenHelper = new WeatherDbHelper(getContext());
		return true;
	}

	   @Override
	    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
	                        String sortOrder) {
	        // Here's the switch statement that, given a URI, will determine what kind of request it is,
	        // and query the database accordingly.
	        Cursor retCursor;
	        switch (sUriMatcher.match(uri)) {
	            // "weather/*/*"
	            case WEATHER_WITH_LOCATION_AND_DATE:
	            {
	                retCursor = null;
	                break;
	            }
	            // "weather/*"
	            case WEATHER_WITH_LOCATION: {
	                retCursor = null;
	                break;
	            }
	            // "weather"
	            case WEATHER: {
	                retCursor = mOpenHelper.getReadableDatabase().query(
	                        WeatherContract.WeatherEntry.TABLE_NAME,
	                        projection,
	                        WeatherContract.LocationEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
	                        null,
	                        null,
	                        null,
	                        sortOrder
	                );
	                break;
	            }
	            // "location/*"
	            case LOCATION_ID: {
	            	
	            	long id = ContentUris.parseId(uri);
	            	
	            	retCursor = mOpenHelper.getReadableDatabase().query(
	                        WeatherContract.LocationEntry.TABLE_NAME,
	                        projection,
	                        selection,
	                        selectionArgs,
	                        null,
	                        null,
	                        sortOrder
	            	);	                
	                break;
	            }
	            // "location"
	            case LOCATION: {
	                retCursor = mOpenHelper.getReadableDatabase().query(
	                        WeatherContract.LocationEntry.TABLE_NAME,
	                        projection,
	                        selection,
	                        selectionArgs,
	                        null,
	                        null,
	                        sortOrder
	                );
	                break;
	            }
	 
	            default:
	                throw new UnsupportedOperationException("Unknown uri: " + uri);
	        }
	        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
	        return retCursor;
	    }

	@Override
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);
		
        switch (match) {
        case WEATHER_WITH_LOCATION_AND_DATE:
            return WeatherContract.WeatherEntry.CONTENT_ITEM_TYPE;
        case WEATHER_WITH_LOCATION:
            return WeatherContract.WeatherEntry.CONTENT_TYPE;
        case WEATHER:
            return WeatherContract.WeatherEntry.CONTENT_TYPE;
        case LOCATION_ID:
        	return WeatherContract.LocationEntry.CONTENT_ITEM_TYPE;
        case LOCATION:
        	return WeatherContract.LocationEntry.CONTENT_TYPE;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}



}
