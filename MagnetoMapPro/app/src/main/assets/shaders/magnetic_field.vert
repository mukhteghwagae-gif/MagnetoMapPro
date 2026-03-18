#version 310 es
precision highp float;

in vec3 a_Position;
in vec3 a_Normal;
in vec2 a_TexCoord;

uniform mat4 u_ModelViewProjection;
uniform mat4 u_ModelView;

out vec3 v_Position;
out vec3 v_Normal;
out vec2 v_TexCoord;

void main() {
    v_Position = (u_ModelView * vec4(a_Position, 1.0)).xyz;
    v_Normal = normalize((u_ModelView * vec4(a_Normal, 0.0)).xyz);
    v_TexCoord = a_TexCoord;
    gl_Position = u_ModelViewProjection * vec4(a_Position, 1.0);
}
