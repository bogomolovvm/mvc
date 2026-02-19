package beans;

import org.springframework.stereotype.Repository;
import model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Post> all() {
        return List.copyOf(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = counter.getAndIncrement();
            post.setId(newId);
            posts.put(newId, post);
            return post;
        }
        if (posts.containsKey(post.getId())) {
            posts.put(post.getId(), post);
            return post;
        }
        throw new exception.NotFoundException();
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}