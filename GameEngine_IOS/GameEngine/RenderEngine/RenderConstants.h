#ifndef GameEngine_RenderConstants_h
#define GameEngine_RenderConstants_h

#define VERTEX_SIZE 3
#define COORD_SIZE 2
#define NORMAL_SIZE 3

//Specifies whether fixed-point data values should be normalized (GL_TRUE) or converted directly as fixed-point values (GL_FALSE) when they are accessed.
#define VERTEX_NORMALIZED  0

//Offset between following vertices
#define STRIDE  0

//Offset of start of data
#define START_OFFSET 0

//number of components per generic vertex attribute
#define NUMBER_COMPONENTS_PER_VERTEX_ATTR  2


/**
 * Number of components that compose the normal vector
 */
#define NUMBER_COMPONENTS_PER_NORMAL_ATTR  3


#endif
