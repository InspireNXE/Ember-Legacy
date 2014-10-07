/**
 * This file is part of Ember, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 ObsidianBox <http://obsidianbox.org/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.obsidianbox.ember.game.renderer;

import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector4f;
import com.flowpowered.render.RenderGraph;
import com.flowpowered.render.impl.BlurNode;
import com.flowpowered.render.impl.CascadedShadowMappingNode;
import com.flowpowered.render.impl.LightingNode;
import com.flowpowered.render.impl.RenderGUINode;
import com.flowpowered.render.impl.RenderModelsNode;
import com.flowpowered.render.impl.RenderTransparentModelsNode;
import com.flowpowered.render.impl.SSAONode;
import com.flowpowered.render.impl.ShadowMappingNode;
import org.lwjgl.opengl.GLContext;
import org.obsidianbox.ember.Ember;
import org.obsidianbox.ember.FileSystem;
import org.spout.renderer.api.Camera;
import org.spout.renderer.api.GLImplementation;
import org.spout.renderer.api.GLVersioned;
import org.spout.renderer.api.gl.Context;
import org.spout.renderer.api.gl.Texture;
import org.spout.renderer.api.model.Model;
import org.spout.renderer.lwjgl.LWJGLUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class Renderer extends TickingElement {
    public static final String WINDOW_TITLE = "Ember - " + (Ember.VERSION.isPresent() ? Ember.VERSION.get() : "Unknown");
    public static final Vector2i WINDOW_SIZE = new Vector2i(1024, 768);
    public static final DateFormat SCREENSHOT_DATE_FORMAT = new SimpleDateFormat("MM-dd_HH.mm.ss-yyyy");
    public static final Vector2i SHADOW_MAP_SIZE = new Vector2i(1048, 1048);
    public static final int BLUR_SIZE = 2;
    public final boolean cullBackFaces;
    private final Context context;
    private final RenderGraph graph;
    // Nodes
    private RenderModelsNode modelsNode;
    private ShadowMappingNode shadowMappingNode;
    private LightingNode lightingNode;
    private RenderTransparentModelsNode transparentModelsNode;
    private RenderGUINode guiNode;
    //Models
    private final List<Model> models = new LinkedList<>();
    private final List<Model> guiModels = new LinkedList<>();
    private final List<Model> transparentModels = new LinkedList<>();

    public Renderer(GLVersioned.GLVersion version, boolean cullBackFaces) {
        super("renderer", 60);
        switch (version) {
            case GL20:
                context = GLImplementation.get(LWJGLUtil.GL20_IMPL);
                break;
            case GL21:
                context = GLImplementation.get(LWJGLUtil.GL21_IMPL);
                break;
            case GL30:
            case GL31:
                context = GLImplementation.get(LWJGLUtil.GL30_IMPL);
                break;
            case GL32:
                context = GLImplementation.get(LWJGLUtil.GL32_IMPL);
                break;
            default:
                throw new IllegalArgumentException("Unsupported OpenGL version: " + version);
        }
        this.graph = new RenderGraph(context, FileSystem.SHADERS_GL330_PATH.toString());
        this.cullBackFaces = cullBackFaces;
        constructRenderer();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onTick(long dt) {
        graph.render();
    }

    @Override
    public void onStop() {
    }

    private void constructRenderer() {
        // Setup context
        context.setWindowTitle(WINDOW_TITLE);
        context.setWindowSize(WINDOW_SIZE);
        context.create();
        context.setClearColor(Vector4f.ZERO);
        if (cullBackFaces) {
            context.enableCapability(Context.Capability.CULL_FACE);
        }
        if (GLContext.getCapabilities().GL_ARB_depth_clamp) {
            context.enableCapability(Context.Capability.DEPTH_CLAMP);
        }
        // Setup graph
        graph.create();
        graph.setAttribute("camera", Camera.createPerspective(60, WINDOW_SIZE.getX(), WINDOW_SIZE.getY(), 0.1f, 200));
        graph.setAttribute("outputSize", WINDOW_SIZE);
        graph.setAttribute("lightDirection", Vector3f.UP.negate());
        graph.setAttribute("models", models);
        // Render models
        modelsNode = new RenderModelsNode(graph, "models");
        graph.addNode(modelsNode);
        // Shadows
        shadowMappingNode = new CascadedShadowMappingNode(graph, "shadows");
        shadowMappingNode.connect("normals", "vertexNormals", modelsNode);
        shadowMappingNode.connect("depths", "depths", modelsNode);
        shadowMappingNode.setAttribute("shadowMapSize", SHADOW_MAP_SIZE);
        shadowMappingNode.setAttribute("renderModelsNode", modelsNode);
        shadowMappingNode.setAttribute("kernelSize", 8);
        shadowMappingNode.setAttribute("noiseSize", BLUR_SIZE);
        shadowMappingNode.setAttribute("bias", 0.01f);
        shadowMappingNode.setAttribute("radius", 0.05f);
        graph.addNode(shadowMappingNode);
        // Blur shadows
        final BlurNode blurShadowsNode = new BlurNode(graph, "blurShadows");
        blurShadowsNode.connect("colors", "shadows", shadowMappingNode);
        blurShadowsNode.setAttribute("kernelGenerator", BlurNode.BOX_KERNEL);
        blurShadowsNode.setAttribute("kernelSize", BLUR_SIZE + 1);
        graph.addNode(blurShadowsNode);
        // SSAO
        final SSAONode ssaoNode = new SSAONode(graph, "ssao");
        ssaoNode.connect("normals", "normals", modelsNode);
        ssaoNode.connect("depths", "depths", modelsNode);
        ssaoNode.setAttribute("kernelSize", 8);
        ssaoNode.setAttribute("threshold", 0.15f);
        ssaoNode.setAttribute("noiseSize", BLUR_SIZE);
        ssaoNode.setAttribute("radius", 0.5f);
        ssaoNode.setAttribute("power", 2f);
        graph.addNode(ssaoNode);
        // Blur occlusions
        final BlurNode blurOcclusionsNode = new BlurNode(graph, "blurOcclusions");
        blurOcclusionsNode.connect("colors", "occlusions", ssaoNode);
        blurOcclusionsNode.setAttribute("kernelGenerator", BlurNode.BOX_KERNEL);
        blurOcclusionsNode.setAttribute("kernelSize", BLUR_SIZE + 1);
        blurOcclusionsNode.setAttribute("outputFormat", Texture.InternalFormat.R8);
        graph.addNode(blurOcclusionsNode);
        // Lighting
        lightingNode = new LightingNode(graph, "lighting");
        lightingNode.connect("colors", "colors", modelsNode);
        lightingNode.connect("normals", "normals", modelsNode);
        lightingNode.connect("depths", "depths", modelsNode);
        lightingNode.connect("materials", "materials", modelsNode);
        lightingNode.connect("occlusions", "colors", blurOcclusionsNode);
        lightingNode.connect("shadows", "colors", blurShadowsNode);
        graph.addNode(lightingNode);
        // Transparent models
        transparentModelsNode = new RenderTransparentModelsNode(graph, "transparency");
        transparentModelsNode.connect("depths", "depths", modelsNode);
        transparentModelsNode.connect("colors", "colors", lightingNode);
        transparentModelsNode.setAttribute("transparentModels", transparentModels);
        graph.addNode(transparentModelsNode);
        // Render GUI
        guiNode = new RenderGUINode(graph, "gui");
        guiNode.connect("colors", "colors", modelsNode);
        guiNode.setAttribute("guiModels", guiModels);
        graph.addNode(guiNode);

        graph.updateAll();

        graph.build();
    }
}
