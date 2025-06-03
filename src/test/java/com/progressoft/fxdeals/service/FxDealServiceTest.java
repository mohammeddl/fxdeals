package com.progressoft.fxdeals.service;

import com.progressoft.fxdeals.dto.request.FxDealRequest;
import com.progressoft.fxdeals.dto.response.FxDealResponse;
import com.progressoft.fxdeals.exception.RequestAlreadyExistException;
import com.progressoft.fxdeals.exception.ValidationException;
import com.progressoft.fxdeals.mapper.FxDealMapper;
import com.progressoft.fxdeals.model.FxDeal;
import com.progressoft.fxdeals.repository.FxDealRepository;
import com.progressoft.fxdeals.validation.FxDealValidator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.progressoft.fxdeals.service.impl.FxDealServiceImpl;

@ExtendWith(MockitoExtension.class)
class FxDealServiceTest {
        private static final UUID TEST_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        @Mock
        private FxDealRepository repo;

        @Mock
        private FxDealMapper mapper;

        @Mock
        private FxDealValidator validator;

        @InjectMocks
        private FxDealServiceImpl service;

        private FxDealRequest validRequest() {
                FxDealRequest dto = mock(FxDealRequest.class);
                when(dto.id()).thenReturn(TEST_ID);
                when(dto.fromCurrency()).thenReturn("USD");
                when(dto.toCurrency()).thenReturn("EUR");
                when(dto.amount()).thenReturn(new BigDecimal("100.00"));
                return dto;
        }

        @Test
        void importDeal_Success() {
                FxDealRequest dto = validRequest();
                FxDeal entity = mock(FxDeal.class);
                FxDeal savedEntity = mock(FxDeal.class);
                FxDealResponse resp = mock(FxDealResponse.class);

                doNothing().when(validator).validate(dto);
                when(repo.existsById(TEST_ID)).thenReturn(false);
                when(mapper.toEntity(dto)).thenReturn(entity);
                when(repo.save(entity)).thenReturn(savedEntity);
                when(mapper.toResponse(savedEntity)).thenReturn(resp);

                FxDealResponse actual = service.importDeal(dto);

                assertSame(resp, actual);
                verify(validator).validate(dto);
                verify(repo).existsById(TEST_ID);
                verify(mapper).toEntity(dto);
                verify(repo).save(entity);
                verify(mapper).toResponse(savedEntity);
        }
        
        @Test
        void importDeal_ThrowsRequestAlreadyExistException_WhenDuplicate() {
                FxDealRequest dto = validRequest();

                doNothing().when(validator).validate(dto);
                when(repo.existsById(TEST_ID)).thenReturn(true);

                RequestAlreadyExistException ex = assertThrows(
                                RequestAlreadyExistException.class,
                                () -> service.importDeal(dto));
                assertTrue(ex.getMessage().contains(TEST_ID.toString()));

                verify(validator).validate(dto);
                verify(repo).existsById(TEST_ID);
                verifyNoInteractions(mapper);
        }

        @Test
        void importDeal_PropagatesDataIntegrityViolationException_WhenSaveFails() {
                FxDealRequest dto = validRequest();
                FxDeal entity = mock(FxDeal.class);

                doNothing().when(validator).validate(dto);
                when(repo.existsById(TEST_ID)).thenReturn(false);
                when(mapper.toEntity(dto)).thenReturn(entity);
                when(repo.save(entity))
                                .thenThrow(new DataIntegrityViolationException("DB error"));

                DataIntegrityViolationException ex = assertThrows(
                                DataIntegrityViolationException.class,
                                () -> service.importDeal(dto));
                assertEquals("DB error", ex.getMessage());

                verify(mapper).toEntity(dto);
                verify(repo).save(entity);
        }

        @Test
        void importDeal_PropagatesRuntimeException_WhenMapperFails() {
                FxDealRequest dto = validRequest();

                doNothing().when(validator).validate(dto);
                when(repo.existsById(TEST_ID)).thenReturn(false);
                when(mapper.toEntity(dto))
                                .thenThrow(new RuntimeException("mapping broken"));

                RuntimeException ex = assertThrows(
                                RuntimeException.class,
                                () -> service.importDeal(dto));
                assertEquals("mapping broken", ex.getMessage());

                verify(mapper).toEntity(dto);
                verify(repo, never()).save(any());
        }
}