package com.crowdappz.demo.api;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.crowdappz.demo.model.Location;
import com.crowdappz.demo.wrapper.BooleanWrapper;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

@Api(name = "mainEndpoint", version = "v0")
public class MainEndpoint {

	static {
        ObjectifyService.register(Location.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();//prior to v.4.0 use .begin() ,
        //since v.4.0  use ObjectifyService.ofy();
    }

	@ApiMethod(httpMethod = HttpMethod.POST, path = "storeLocation")
	public BooleanWrapper storeLocation(@Named("userId") String userId,
			@Named("latitude") double latitude,
			@Named("longitude") double longitude) {

		Location location = new Location(userId, latitude, longitude);

		ofy().save().entity(location).now();

		return new BooleanWrapper(true);
	}

	@ApiMethod(httpMethod = HttpMethod.GET, path = "getLocationsFromUser")
	public List<Location> getLocations(@Named("userId") String userId){
		Query<Location> q = ofy().load().type(Location.class).filter("userId", userId);
		return q.list();
	}
}
