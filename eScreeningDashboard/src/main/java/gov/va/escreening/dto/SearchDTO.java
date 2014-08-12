package gov.va.escreening.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A DTO class which is used as a form object in the search form.
 * @author Tonté Pouncil
 */
public class SearchDTO <T> {
    private T searchTerm;

    private SearchType searchType;

    public SearchDTO() {

    }
    public SearchDTO(T searchTerm, SearchType searchType) {
        this.searchTerm = searchTerm;
        this.searchType = searchType;
    }

    public T getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(T searchTerm) {
        this.searchTerm = searchTerm;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
