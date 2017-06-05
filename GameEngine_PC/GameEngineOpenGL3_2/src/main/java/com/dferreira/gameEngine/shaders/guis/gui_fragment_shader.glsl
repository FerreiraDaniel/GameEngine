#version 400 core

/*The coordinates of the texture as input*/
in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main(void) {
	out_Color = texture(textureSampler,textureCoords);
}