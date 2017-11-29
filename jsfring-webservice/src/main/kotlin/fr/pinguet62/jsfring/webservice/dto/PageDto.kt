package fr.pinguet62.jsfring.webservice.dto

import org.springframework.data.domain.Page

/**
 * @see Page
 */
data class PageDto<out T>(
        val results: List<T>,
        val total: Long
)
