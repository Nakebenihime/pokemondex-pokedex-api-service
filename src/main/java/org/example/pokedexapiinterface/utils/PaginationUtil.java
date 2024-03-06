package org.example.pokedexapiinterface.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class PaginationUtil {

    private PaginationUtil() {
    }

    public static PagedModel.PageMetadata getPageMetadata(Page<?> page) {
        return new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
    }

    public static void addPaginationLinks(PagedModel<?> pagedModel, UriComponentsBuilder uriBuilder, Page<?> page, Map<String, String> queryParams) {
        uriBuilder.replaceQueryParam("page");
        uriBuilder.replaceQueryParam("size");
        addQueryParameters(uriBuilder, queryParams);
        addFirstPageLink(pagedModel, uriBuilder, page);
        addNextPageLink(pagedModel, uriBuilder, page);
        addPreviousPageLink(pagedModel, uriBuilder, page);
        addLastPageLink(pagedModel, uriBuilder, page);
    }

    private static void addFirstPageLink(PagedModel<?> pagedModel, UriComponentsBuilder uriBuilder, Page<?> page) {
        if (!page.isFirst()) {
            addPageLink(pagedModel, uriBuilder, 0, page.getSize(), page.getSort(), "first");
        }
    }

    private static void addNextPageLink(PagedModel<?> pagedModel, UriComponentsBuilder uriBuilder, Page<?> page) {
        if (page.hasNext()) {
            addPageLink(pagedModel, uriBuilder, page.getNumber() + 1, page.getSize(), page.getSort(), "next");
        }
    }

    private static void addPreviousPageLink(PagedModel<?> pagedModel, UriComponentsBuilder uriBuilder, Page<?> page) {
        if (page.hasPrevious()) {
            addPageLink(pagedModel, uriBuilder, page.getNumber() - 1, page.getSize(), page.getSort(), "previous");
        }
    }

    private static void addLastPageLink(PagedModel<?> pagedModel, UriComponentsBuilder uriBuilder, Page<?> page) {
        if (!page.isLast()) {
            addPageLink(pagedModel, uriBuilder, page.getTotalPages() - 1, page.getSize(), page.getSort(), "last");
        }
    }

    private static void addPageLink(PagedModel<?> pagedModel, UriComponentsBuilder uriBuilder, int page, int size, Sort sort, String rel) {
        uriBuilder.replaceQueryParam("page", page);
        uriBuilder.replaceQueryParam("size", size);
        addSortParameters(uriBuilder, sort);
        Link link = Link.of(uriBuilder.toUriString(), rel);
        pagedModel.add(link);
    }

    private static void addSortParameters(UriComponentsBuilder uriBuilder, Sort sort) {
        if (sort != null) {
            uriBuilder.replaceQueryParam("sort");
            sort.forEach(order -> uriBuilder.queryParam("sort", String.format("%s,%s", order.getProperty(), order.getDirection())));
        }
    }

    public static void addQueryParameters(UriComponentsBuilder uriBuilder, Map<String, String> queryParams) {
        if (queryParams != null && !queryParams.isEmpty()) {
            queryParams.forEach(uriBuilder::queryParam);
        }
    }
}