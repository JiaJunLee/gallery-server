package com.oocl.ita.gallery.file

import com.oocl.ita.gallery.common.BaseService
import com.oocl.ita.gallery.image.Image
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.powermock.api.support.membermodification.MemberMatcher
import org.powermock.reflect.Whitebox
import org.springframework.mock.web.MockMultipartFile

import static org.powermock.api.support.membermodification.MemberModifier.suppress

class FileServiceTest {

    @InjectMocks
    private FileService fileService = new FileService()

    @Mock
    private FileRepository fileRepository


    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void should_return_fileRepository_when_getRepository() {
        //When
        FileRepository fileRepository1 = fileService.getRepository()

        //Then
        Assert.assertNotNull(fileRepository1)
    }


    @Test
    void should_return_image_null_when_saveFile_mock_save() {
        //Given
        suppress(MemberMatcher.methodsDeclaredIn(BaseService.class))

        //When
        Image image = fileService.saveFile("id1",new MockMultipartFile("aa","aa".getBytes()))

        //Then
        Assert.assertNull(image)
    }

    @Test
    void should_return_user_null_when_findByUsername_given_user_name_a() {
        //Given
        suppress(MemberMatcher.methodsDeclaredIn(BaseService.class))
        String id = "v1"

        //When
        ImageFile image = (ImageFile)Whitebox.invokeMethod(fileService,"constructImageFile",id,new MockMultipartFile("aa","aa".getBytes()))

        //Then
        Assert.assertNotNull(image)
        Assert.assertEquals(id,image.id)
    }


}
