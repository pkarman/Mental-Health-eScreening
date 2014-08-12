package gov.va.escreening.dto;

/**
 * Describes the search type of the search. Legal values are:
 * <ul>
 *     <li>METHOD_NAME which means that the query is obtained from the method name of the query method.</li>
 *     <li>NAMED_QUERY which means that a named query is used.</li>
 *     <li>QUERY_ANNOTATION which means that the query method annotated with @Query annotation is used.</li>
 * </ul>
 * @author Tonté Pouncil
 */
public enum SearchType  {
    METHOD_NAME,
    NAMED_QUERY,
    QUERY_ANNOTATION;
}