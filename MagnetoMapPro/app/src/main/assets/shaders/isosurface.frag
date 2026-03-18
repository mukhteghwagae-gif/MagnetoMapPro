#version 310 es
precision highp float;

in vec3 v_Position;
in vec3 v_Normal;

out vec4 FragColor;

uniform vec3 u_LightDir;
uniform vec3 u_LightColor;
uniform vec3 u_AmbientColor;
uniform vec3 u_CameraPos;
uniform vec3 u_IsosurfaceColor; // e.g., red for high anomaly
uniform float u_SubsurfaceScattering;

void main() {
    vec3 normal = normalize(v_Normal);
    vec3 viewDir = normalize(u_CameraPos - v_Position);
    vec3 lightDir = normalize(u_LightDir);
    
    // Diffuse
    float diff = max(dot(normal, lightDir), 0.0);
    vec3 diffuse = diff * u_LightColor;
    
    // Specular (Phong)
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32.0);
    vec3 specular = 0.5 * spec * u_LightColor;
    
    // Subsurface scattering approximation (wrap lighting)
    float wrap = 0.5;
    float scatter = max(0.0, (dot(normal, lightDir) + wrap) / (1.0 + wrap));
    vec3 sss = u_SubsurfaceScattering * scatter * u_IsosurfaceColor;
    
    // Rim lighting for glowing effect
    float rim = 1.0 - max(dot(viewDir, normal), 0.0);
    rim = smoothstep(0.6, 1.0, rim);
    vec3 rimLight = rim * u_IsosurfaceColor * 2.0;

    vec3 result = (u_AmbientColor + diffuse + sss) * u_IsosurfaceColor + specular + rimLight;
    
    FragColor = vec4(result, 0.85); // Slightly transparent
}
