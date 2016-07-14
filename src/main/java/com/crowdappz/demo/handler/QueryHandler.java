package com.crowdappz.demo.handler;

import com.crowdappz.demo.util.LoggerUtils;
import com.crowdappz.demo.util.ResponseMessageUtils;
import com.crowdappz.demo.util.StringUtils;
import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

import java.util.*;

import static com.googlecode.objectify.ObjectifyService.ofy;


/**
 * This class is responsible for generic (CRUD) queries
 */
public class QueryHandler {

    // ================ Constants ============================================== //

    // ================ Members ================================================ //

    // ================ Constructors & Main ==================================== //

    // ================ Methods for/from SuperClass / Interfaces =============== //

    // ================ Methods ================================================ //

    /**
     * This method is called on server start from
     * DropContextListener.contextInitialized to initialize the entities that
     * will be handled by Objectify.
     */
    public static void registerClazzez() {

    }

    /**
     * Returns a list of all objects which match the filter criteria.
     *
     * @param clazz   the class which objects should be filtered
     * @param filters filter criteria to query the objects of the class
     * @return a list of all objects which match the filter criteria or an empty list if none matches
     */
    public static <T> List<T> fetchWithFilters(Class<T> clazz, Map<String, Object> filters) {
        if (filters == null || clazz == null) {
            return new LinkedList<T>();
        }

        Query<T> query = null;
        for (String filterKey : filters.keySet()) {
            if (query == null) {
                query = ofy().load().type(clazz).filter(filterKey, filters.get(filterKey));
            } else {
                query = query.filter(filterKey, filters.get(filterKey));
            }
        }

        return QueryHandler.getListFromQuery(query);
    }

    /**
     * Returns a single (the first) object that matches the filter criteria.
     *
     * @param clazz   the class which objects should be filtered
     * @param filters filter criteria to query the objects of the class
     * @return the first object that matches all of the given filter criteria or null if none matches
     */
    public static <T> T fetchSingleWithFilter(Class<T> clazz, Map<String, Object> filters) {
        Query<T> query = null;
        for (String filterKey : filters.keySet()) {
            if (query == null) {
                query = ofy().load().type(clazz).filter(filterKey, filters.get(filterKey));
            } else {
                query = query.filter(filterKey, filters.get(filterKey));
            }
        }

        return query.first().now();
    }

    /**
     * Fetches all objects for the specified class.
     *
     * @param clazz the class which objects should be fetched
     * @return a list of all objects of the given. May be an empty list
     */
    public static <T> List<T> fetchAll(Class<T> clazz) {
        return QueryHandler.getListFromQuery(ofy().load().type(clazz));
    }

    /**
     * Fetches an object specified by the given id.
     *
     * @param id    the id of the object to fetch.
     * @param clazz the class of the object to fetch
     * @return the object specified by the id or null if not found
     */
    public static <T> T loadById(Long id, Class<T> clazz) {
        if (id == null) {
            return null;
        }
        return ofy().load().key(Key.create(clazz, id)).now();
    }

    /**
     * Fetches an object specified by the given id.
     *
     * @param id    the id of the object to fetch.
     * @param clazz the class of the object to fetch
     * @return the object specified by the id or null if not found
     */
    public static <T> T loadById(String id, Class<T> clazz) {
        if (StringUtils.isNullOrEmpty(id)) {
            return null;
        }
        return ofy().load().key(Key.create(clazz, id)).now();
    }

    /**
     * Returns a map with <EntityKey, Entity>, whereby EntityKey must either be a String or a Long.
     *
     * @param ids   an id set of Strings or Longs.
     * @param clazz
     * @return
     */
    public static <S, T> Map<S, T> loadByIds(Set<S> ids, Class<T> clazz) {
        return ofy().load().type(clazz).ids(ids);
    }

    /**
     * Returns a list with all keys belonging to the passed Entity class. (Minor datastore operation)
     *
     * @param clazz
     * @return
     */
    public static <T> List<Key<T>> loadKeys(Class<T> clazz) {
        return ofy().load().type(clazz).keys().list();
    }

    /**
     * Stores the object. Throws an InternalServerErrorException if something goes wrong.
     *
     * @param object the object that should be stored
     * @throws ServiceException
     */
    public static void save(Object object) throws ServiceException {
        try {
            ofy().save().entity(object).now();
        } catch (Exception err) {
            LoggerUtils.logError(ResponseMessageUtils.INTERNAL_SERVER_ERROR, err);
            throw new InternalServerErrorException(ResponseMessageUtils.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Save all passed objects in a batch process.
     *
     * @param objects
     * @throws ServiceException
     */
    public static void saveBatch(Collection<? extends Object> objects) throws ServiceException {
        try {
            ofy().save().entities(objects).now();
        } catch (Exception err) {
            LoggerUtils.logError(ResponseMessageUtils.INTERNAL_SERVER_ERROR, err);
            throw new InternalServerErrorException(ResponseMessageUtils.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes the object specified by the given id. Throws an InternalServerErrorException if something goes wrong.
     *
     * @param id    the id of the object to delete
     * @param clazz the class of the object to delete
     * @throws ServiceException
     */
    public static <T> void deleteById(Long id, Class<T> clazz) throws ServiceException {
        try {
            ofy().delete().key(Key.create(clazz, id)).now();
        } catch (Exception err) {
            LoggerUtils.logError(ResponseMessageUtils.INTERNAL_SERVER_ERROR, err);
            throw new InternalServerErrorException(ResponseMessageUtils.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes the object specified by the given id. Throws an InternalServerErrorException if something goes wrong.
     *
     * @param id    the id of the object to delete
     * @param clazz the class of the object to delete
     * @throws ServiceException
     */
    public static <T> void deleteById(String id, Class<T> clazz) throws ServiceException {
        try {
            ofy().delete().key(Key.create(clazz, id)).now();
        } catch (Exception err) {
            LoggerUtils.logError(ResponseMessageUtils.INTERNAL_SERVER_ERROR, err);
            throw new InternalServerErrorException(ResponseMessageUtils.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deleted all objects belonging to the ids in the passed set. Throws an InternalServerErrorException if something goes wrong.
     *
     * @param ids   must be from type Long or String.
     * @param clazz
     * @throws ServiceException
     */
    public static <T> void deleteByIds(Set<?> ids, Class<T> clazz) throws ServiceException {
        try {
            ofy().delete().type(clazz).ids(ids).now();
        } catch (Exception err) {
            LoggerUtils.logError(ResponseMessageUtils.INTERNAL_SERVER_ERROR, err);
            throw new InternalServerErrorException(ResponseMessageUtils.INTERNAL_SERVER_ERROR);
        }
    }

    private static <T> List<T> getListFromQuery(Query<T> query) {
        QueryResultIterator<T> iterator = query.iterator();
        List<T> list = new LinkedList<T>();

        while (iterator.hasNext()) {
            list.add(iterator.next());
        }

        return list;
    }

    public static <T> int getCount(Class<T> clazz) throws ServiceException {
        try {
            return ofy().load().type(clazz).count();
        } catch (Exception e) {
            LoggerUtils.logError(ResponseMessageUtils.INTERNAL_SERVER_ERROR, e);
            throw new InternalServerErrorException(ResponseMessageUtils.INTERNAL_SERVER_ERROR);
        }
    }
    // ================ Getter & Setter ======================================== //

    // ================ Inner & Anonymous Classes ============================== //
}