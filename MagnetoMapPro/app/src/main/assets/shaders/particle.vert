#version 310 es
precision highp float;

in vec3 a_Position;
in float a_FieldStrength;
in float a_Age;

uniform mat4 u_ModelViewProjection;
uniform vec3 u_CameraPos;

out float v_FieldStrength;
out float v_Age;

void main() {
    v_FieldStrength = a_FieldStrength;
    v_Age = a_Age;
    
    gl_Position = u_ModelViewProjection * vec4(a_Position, 1.0);
    
    // Size depends on distance and field strength
    float dist = distance(a_Position, u_CameraPos);
    gl_PointSize = max(2.0, (20.0 / (dist + 0.1)) * (a_FieldStrength + 0.5));
}
