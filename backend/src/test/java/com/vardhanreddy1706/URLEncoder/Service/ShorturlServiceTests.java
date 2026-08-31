package com.vardhanreddy1706.URLEncoder.Service;

import com.vardhanreddy1706.URLEncoder.Models.Shorturl;
import com.vardhanreddy1706.URLEncoder.Repository.ShorturlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShorturlServiceTests {

    @Mock
    private ShorturlRepository shorturlRepository;

    @Test
    void createsAndSavesShortUrlWithSixCharacterKey() {
        when(shorturlRepository.existsByShortKey(anyString())).thenReturn(false);
        when(shorturlRepository.save(any(Shorturl.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ShorturlService service = new ShorturlService(shorturlRepository);

        Shorturl result = service.createShorturl("https://www.sivalabs.in/");

        assertThat(result.getOriginalUrl()).isEqualTo("https://www.sivalabs.in/");
        assertThat(result.getShortKey()).matches("[A-Za-z0-9]{6}");
        assertThat(result.getClickCount()).isZero();
        assertThat(result.getCreatedAt()).isNotNull();
        verify(shorturlRepository).save(result);
    }
}
