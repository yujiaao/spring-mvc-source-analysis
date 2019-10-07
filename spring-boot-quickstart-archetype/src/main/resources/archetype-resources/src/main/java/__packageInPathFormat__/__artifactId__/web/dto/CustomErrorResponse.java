package ${package}.${artifactId}.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.Serializable;

/**
 * Necessary for proper Swagger documentation.
 *
 * @author xwx
 */
@SuppressWarnings("unused")
@AllArgsConstructor
@Getter
public class CustomErrorResponse implements Serializable {


    private String errorCode;

    private String errorMessage;

}
