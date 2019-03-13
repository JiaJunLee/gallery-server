package com.oocl.ita.gallery.common

import spock.lang.Specification

class BaseDocumentTest extends Specification{

    def 'should return BaseDocument when new BaseDocument given id createDate lastModifiedDate'() {
        given:
        def id = "1"

        when:
        BaseDocument baseDocument = new BaseDocument(id:id,lastModifiedDate: new Date())


        then:
        baseDocument.id == id
        baseDocument.lastModifiedDate != null
        baseDocument.createdDate != null

    }

}
