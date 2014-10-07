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
package org.obsidianbox.ember.game.universe.material;

import com.flowpowered.commons.StringToUniqueIntegerMap;
import org.obsidianbox.ember.api.universe.material.Material;
import org.obsidianbox.ember.api.universe.material.MaterialRegistrationException;
import org.obsidianbox.ember.api.universe.material.MaterialStore;
import org.obsidianbox.ember.api.universe.material.builtin.None;
import org.obsidianbox.ember.api.universe.material.builtin.Solid;

import java.util.LinkedList;
import java.util.Optional;

public class MaterialStoreImpl implements MaterialStore {

    public final None NONE;
    public final Solid SOLID;
    private final StringToUniqueIntegerMap materialIdMap = new StringToUniqueIntegerMap("Material ID Map");
    private final LinkedList<Material> instances = new LinkedList<>();
    private boolean locked = false;

    public MaterialStoreImpl() {
        try {
            NONE = (None) register(new None());
            SOLID = (Solid) register(new Solid());
        } catch (MaterialRegistrationException e) {
            throw new RuntimeException("Should never be possible to reach here...");
        }
    }

    @Override
    public Material register(Material material) throws MaterialRegistrationException {
        if (locked) {
            throw new MaterialRegistrationException("Registering a material is not possible as the manager has been locked!");
        }
        if (material == null) {
            throw new MaterialRegistrationException("Attempt made to register a null material!");
        }
        if (material.getName() == null) {
            throw new MaterialRegistrationException("Attempt made to register a material with a null name!");
        }
        if (material.getName().isEmpty()) {
            throw new MaterialRegistrationException("Attempt made to register a material with an empty name!");
        }
        final Integer existing = materialIdMap.getValue(material.getName());
        if (existing != null) {
            throw new MaterialRegistrationException("This material's identifier has been registered before! Choose a new name please");
        }
        final Integer registered = materialIdMap.register(material.getName());
        instances.add(registered, material);
        return material;
    }

    @Override
    public Optional<Material> get(String identifier) {
        final Integer existing = materialIdMap.getValue(identifier);
        if (existing == null) {
            return Optional.empty();
        }
        return Optional.of(instances.get(existing));
    }

    @Override
    public Optional<String> get(Class<? extends Material> clazz) {
        for (int i = 0; i < instances.size(); i++) {
            if (instances.get(i).getClass() == clazz) {
                return Optional.ofNullable(materialIdMap.getString(i));
            }
        }
        return Optional.empty();
    }

    public StringToUniqueIntegerMap getMaterialIdMap() {
        return materialIdMap;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
