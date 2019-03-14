package com.oocl.ita.gallery.common

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

        getRepository().deleteById(id)
        return true

    }

    boolean deleteAll(List<ID> ids) {

        ids.each { getRepository().deleteById(it) }
        return true

    }

    Iterable<T> findAll() {
        return getRepository().findAll()
    }

}
