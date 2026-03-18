#version 310 es
precision highp float;

in vec3 a_Position;
in vec3 a_Normal;

uniform mat4 u_ModelViewProjection;
uniform mat4 u_ModelView;
uniform mat3 u_NormalMatrix;

out vec3 v_Position;
out vec3 v_Normal;

void main() {
    v_Position = (u_ModelView * vec4(a_Position, 1.0)).xyz;
    v_Normal = normalize(u_NormalMatrix * a_Normal);
    gl_Position = u_ModelViewProjection * vec4(a_Position, 1.0);
}
