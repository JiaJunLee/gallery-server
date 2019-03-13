package com.oocl.ita.gallery.common

import org.springframework.data.repository.PagingAndSortingRepository
import spock.lang.Specification

class DefaultBaseServiceTest extends Specification{

    def 'should return null when getRepository'() {
        given:
        DefaultBaseService defaultBaseService = new DefaultBaseService()

        when:
        PagingAndSortingRepository repository = defaultBaseService.getRepository()


        then:
        repository == null
    }
}
