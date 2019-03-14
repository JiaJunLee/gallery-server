package com.oocl.ita.gallery.common

import com.oocl.ita.gallery.common.exception.GalleryRunTimeException
import org.springframework.data.repository.PagingAndSortingRepository

/**
 *
 * BaseService
 *
 * @date 3/10/2019 12:44 PM
 * @version 1.0
 *
 */
abstract class BaseService<T, ID> {

    abstract PagingAndSortingRepository<T, ID> getRepository()

    boolean isExists(ID id) {
        Optional<T> optional = getRepository().findById(id)
        return optional != null && optional.isPresent()
    }

    T findById(ID id) {
        Optional<T> optional = getRepository().findById(id)
        if (optional != null && optional.isPresent()) {
            return optional.get()
        } else {
            return null
        }
    }

    T save(T t) {
        if (t instanceof BaseDocument) {
            t.lastModifiedDate = new Date()
        }
        return getRepository().save(t)
    }

    Iterable<T> saveAll(Iterable<T> ts) {
        return getRepository().saveAll(ts)
    }

    boolean deleteById(ID id) {
        try {
            getRepository().deleteById(id)
            return true
        } catch (Exception e) {
            throw new GalleryRunTimeException(e)
        }
        return false
    }

    boolean deleteAll(List<ID> ids) {
        try {
            ids.each { getRepository().deleteById(it) }
            return true
        } catch (Exception e) {
            throw new GalleryRunTimeException(e)
        }
        return false
    }

    Iterable<T> findAll() {
        return getRepository().findAll()
    }

}
