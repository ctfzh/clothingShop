package com.tl.customclothing.model.dao;

import com.tl.customclothing.model.database.RequestCacheRealm;

import io.realm.Realm;

/**
 * Created by tianlin on 2017/4/14.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class RequestCacheDao
{
    public static RequestCacheRealm queryByKey(String key)
    {

        Realm realm = Realm.getDefaultInstance();

        RequestCacheRealm requestCacheRealm = null;
        try
        {
            // 如果key是一样的，则新的记录不会保存进来，所以缓存时不能太长
            RequestCacheRealm result = realm.where(RequestCacheRealm.class).equalTo("key", key).findFirst();

            requestCacheRealm = realm.copyFromRealm(result);
        } catch (Exception e)
        {

        } finally
        {
            if (!realm.isClosed())
                realm.close();
        }

        return requestCacheRealm;
    }

    public static void insert(final RequestCacheRealm requestCacheRealm)
    {
        Realm realm = Realm.getDefaultInstance();

        try
        {
            realm.executeTransaction(new Realm.Transaction()
            {
                @Override
                public void execute(Realm realm)
                {
                    realm.insertOrUpdate(requestCacheRealm);
                }
            });
        } catch (Exception e)
        {
        } finally
        {
            if (!realm.isClosed())
                realm.close();
        }
    }

    public static void deleteAll()
    {
        Realm realm = Realm.getDefaultInstance();
        try
        {
            realm.executeTransaction(new Realm.Transaction()
            {
                @Override
                public void execute(Realm realm)
                {
                    realm.where(RequestCacheRealm.class).findAll().deleteAllFromRealm();
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (!realm.isClosed())
                realm.close();
        }
    }
}
