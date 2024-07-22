package mubex.renewal_foodsns.domain.mapper;

/**
 * mappable
 *
 * @param <D> DTO
 * @param <E> ENTITY
 */
public interface Mappable<D, E> {

    D toResponse(E e);
}
