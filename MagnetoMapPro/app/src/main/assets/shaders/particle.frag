#version 310 es
precision highp float;

in float v_FieldStrength;
in float v_Age;

out vec4 FragColor;

vec3 jet(float v) {
    vec3 c = vec3(1.0);
    c.r = clamp(1.5 - abs(4.0 * v - 3.0), 0.0, 1.0);
    c.g = clamp(1.5 - abs(4.0 * v - 2.0), 0.0, 1.0);
    c.b = clamp(1.5 - abs(4.0 * v - 1.0), 0.0, 1.0);
    return c;
}

void main() {
    // Make particles circular
    vec2 coord = gl_PointCoord - vec2(0.5);
    if(length(coord) > 0.5) {
        discard;
    }
    
    // Soft edge
    float alpha = 1.0 - (length(coord) * 2.0);
    
    // Fade out based on age
    alpha *= (1.0 - v_Age);
    
    vec3 color = jet(v_FieldStrength);
    
    // Add a bright core
    color += vec3(pow(alpha, 3.0));

    FragColor = vec4(color, alpha * 0.9);
}
