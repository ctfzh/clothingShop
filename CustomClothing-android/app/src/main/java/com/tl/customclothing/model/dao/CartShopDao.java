package com.tl.customclothing.model.dao;

import com.tl.customclothing.model.database.CartShopRealm;
import com.tl.customclothing.utils.NullUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by tianlin on 2017/4/24.
 * Tel : 15071485692
 * QQ : 953108373
 * Function :
 */

public class CartShopDao
{

    public static void insert(final CartShopRealm cartShopRealm)
    {
        Realm realm = Realm.getDefaultInstance();
        try
        {
            realm.executeTransaction(new Realm.Transaction()
            {
                @Override
                public void execute(Realm realm)
                {
                    realm.insertOrUpdate(cartShopRealm);
                }
            });
        } catch (Exception e)
        {

        } finally
        {
            realm.close();
        }
    }

    public static List<CartShopRealm> queryAll()
    {
        List<CartShopRealm> cartShopRealms = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try
        {
            RealmResults<CartShopRealm> realmResults = realm.where(CartShopRealm.class).findAll().sort("time", Sort.DESCENDING);
            cartShopRealms = realm.copyFromRealm(realmResults);
        } catch (Exception e)
        {
        } finally
        {
            realm.close();
        }

        return cartShopRealms;
    }

    public static void delete(final int shopId)
    {
        Realm realm = Realm.getDefaultInstance();

        try
        {
            realm.executeTransaction(new Realm.Transaction()
            {
                @Override
                public void execute(Realm realm)
                {
                    RealmResults<CartShopRealm> realmResults = realm.where(CartShopRealm.class).equalTo("shopId", shopId).findAll();

                    if (!NullUtils.isEmpty(realmResults))
                    {
                        realmResults.get(0).deleteFromRealm();
                    }
                }
            });
        } catch (Exception e)
        {

        } finally
        {
            realm.close();
        }
    }

}
