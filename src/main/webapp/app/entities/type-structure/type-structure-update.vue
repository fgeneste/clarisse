<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="clarisseApp.typeStructure.home.createOrEditLabel"
          data-cy="TypeStructureCreateUpdateHeading"
          v-text="$t('clarisseApp.typeStructure.home.createOrEditLabel')"
        >
          Create or edit a TypeStructure
        </h2>
        <div>
          <div class="form-group" v-if="typeStructure.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="typeStructure.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('clarisseApp.typeStructure.codeTypeStructure')"
              for="type-structure-codeTypeStructure"
              >Code Type Structure</label
            >
            <input
              type="text"
              class="form-control"
              name="codeTypeStructure"
              id="type-structure-codeTypeStructure"
              data-cy="codeTypeStructure"
              :class="{ valid: !$v.typeStructure.codeTypeStructure.$invalid, invalid: $v.typeStructure.codeTypeStructure.$invalid }"
              v-model="$v.typeStructure.codeTypeStructure.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.typeStructure.libelle')" for="type-structure-libelle">Libelle</label>
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="type-structure-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.typeStructure.libelle.$invalid, invalid: $v.typeStructure.libelle.$invalid }"
              v-model="$v.typeStructure.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.typeStructure.libelleCourt')" for="type-structure-libelleCourt"
              >Libelle Court</label
            >
            <input
              type="text"
              class="form-control"
              name="libelleCourt"
              id="type-structure-libelleCourt"
              data-cy="libelleCourt"
              :class="{ valid: !$v.typeStructure.libelleCourt.$invalid, invalid: $v.typeStructure.libelleCourt.$invalid }"
              v-model="$v.typeStructure.libelleCourt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.typeStructure.libellePluriel')" for="type-structure-libellePluriel"
              >Libelle Pluriel</label
            >
            <input
              type="text"
              class="form-control"
              name="libellePluriel"
              id="type-structure-libellePluriel"
              data-cy="libellePluriel"
              :class="{ valid: !$v.typeStructure.libellePluriel.$invalid, invalid: $v.typeStructure.libellePluriel.$invalid }"
              v-model="$v.typeStructure.libellePluriel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.typeStructure.urlComplete')" for="type-structure-urlComplete"
              >Url Complete</label
            >
            <input
              type="text"
              class="form-control"
              name="urlComplete"
              id="type-structure-urlComplete"
              data-cy="urlComplete"
              :class="{ valid: !$v.typeStructure.urlComplete.$invalid, invalid: $v.typeStructure.urlComplete.$invalid }"
              v-model="$v.typeStructure.urlComplete.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.typeStructure.urlSimplifie')" for="type-structure-urlSimplifie"
              >Url Simplifie</label
            >
            <input
              type="text"
              class="form-control"
              name="urlSimplifie"
              id="type-structure-urlSimplifie"
              data-cy="urlSimplifie"
              :class="{ valid: !$v.typeStructure.urlSimplifie.$invalid, invalid: $v.typeStructure.urlSimplifie.$invalid }"
              v-model="$v.typeStructure.urlSimplifie.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.typeStructure.ordre')" for="type-structure-ordre">Ordre</label>
            <input
              type="number"
              class="form-control"
              name="ordre"
              id="type-structure-ordre"
              data-cy="ordre"
              :class="{ valid: !$v.typeStructure.ordre.$invalid, invalid: $v.typeStructure.ordre.$invalid }"
              v-model.number="$v.typeStructure.ordre.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.typeStructure.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./type-structure-update.component.ts"></script>
