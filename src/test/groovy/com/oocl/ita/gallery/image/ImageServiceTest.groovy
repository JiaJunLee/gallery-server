package com.oocl.ita.gallery.image

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest
class ImageServiceTest{

    @Autowired ImageService imageService

    @Test
    void should_return_not_null_when_get_repository(){
        Assert.assertNotNull(imageService.getRepository())
    }

}


