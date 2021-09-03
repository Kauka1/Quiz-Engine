package engine.business;

import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service for providing objects for the response class
 */
@Service
public class ResponseService {
    private static List<Response> response = List.of(
            new Response(true, "Congratulations, you're right!", new boolean[]{true, true, true, true}),
            new Response(false, "Wrong answer! Please, try again.")
    );

    /**
     * Returns a list of object depending on the index
     * @param index List of an object, accepts 0 or 1
     * @return 0 returns the correct answer response. 1 returns the incorrect answer response.
     */
    public Response getResponse(int index){
        return response.get(index);
    }
}
