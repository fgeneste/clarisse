<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="clarisseApp.structure.home.createOrEditLabel"
          data-cy="StructureCreateUpdateHeading"
          v-text="$t('clarisseApp.structure.home.createOrEditLabel')"
        >
          Create or edit a Structure
        </h2>
        <div>
          <div class="form-group" v-if="structure.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="structure.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.structure.codeStructure')" for="structure-codeStructure"
              >Code Structure</label
            >
            <input
              type="text"
              class="form-control"
              name="codeStructure"
              id="structure-codeStructure"
              data-cy="codeStructure"
              :class="{ valid: !$v.structure.codeStructure.$invalid, invalid: $v.structure.codeStructure.$invalid }"
              v-model="$v.structure.codeStructure.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.structure.dateDeCreation')" for="structure-dateDeCreation"
              >Date De Creation</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="structure-dateDeCreation"
                  v-model="$v.structure.dateDeCreation.$model"
                  name="dateDeCreation"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="structure-dateDeCreation"
                data-cy="dateDeCreation"
                type="text"
                class="form-control"
                name="dateDeCreation"
                :class="{ valid: !$v.structure.dateDeCreation.$invalid, invalid: $v.structure.dateDeCreation.$invalid }"
                v-model="$v.structure.dateDeCreation.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.structure.dateDeCloture')" for="structure-dateDeCloture"
              >Date De Cloture</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="structure-dateDeCloture"
                  v-model="$v.structure.dateDeCloture.$model"
                  name="dateDeCloture"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="structure-dateDeCloture"
                data-cy="dateDeCloture"
                type="text"
                class="form-control"
                name="dateDeCloture"
                :class="{ valid: !$v.structure.dateDeCloture.$invalid, invalid: $v.structure.dateDeCloture.$invalid }"
                v-model="$v.structure.dateDeCloture.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.structure.codeAmeli')" for="structure-codeAmeli">Code Ameli</label>
            <input
              type="text"
              class="form-control"
              name="codeAmeli"
              id="structure-codeAmeli"
              data-cy="codeAmeli"
              :class="{ valid: !$v.structure.codeAmeli.$invalid, invalid: $v.structure.codeAmeli.$invalid }"
              v-model="$v.structure.codeAmeli.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.structure.codeSirpas')" for="structure-codeSirpas">Code Sirpas</label>
            <input
              type="text"
              class="form-control"
              name="codeSirpas"
              id="structure-codeSirpas"
              data-cy="codeSirpas"
              :class="{ valid: !$v.structure.codeSirpas.$invalid, invalid: $v.structure.codeSirpas.$invalid }"
              v-model="$v.structure.codeSirpas.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.structure.codeReprographie')" for="structure-codeReprographie"
              >Code Reprographie</label
            >
            <input
              type="text"
              class="form-control"
              name="codeReprographie"
              id="structure-codeReprographie"
              data-cy="codeReprographie"
              :class="{ valid: !$v.structure.codeReprographie.$invalid, invalid: $v.structure.codeReprographie.$invalid }"
              v-model="$v.structure.codeReprographie.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.structure.article')" for="structure-article">Article</label>
            <input
              type="text"
              class="form-control"
              name="article"
              id="structure-article"
              data-cy="article"
              :class="{ valid: !$v.structure.article.$invalid, invalid: $v.structure.article.$invalid }"
              v-model="$v.structure.article.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.structure.libelle')" for="structure-libelle">Libelle</label>
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="structure-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.structure.libelle.$invalid, invalid: $v.structure.libelle.$invalid }"
              v-model="$v.structure.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.structure.libelleCourt')" for="structure-libelleCourt"
              >Libelle Court</label
            >
            <input
              type="text"
              class="form-control"
              name="libelleCourt"
              id="structure-libelleCourt"
              data-cy="libelleCourt"
              :class="{ valid: !$v.structure.libelleCourt.$invalid, invalid: $v.structure.libelleCourt.$invalid }"
              v-model="$v.structure.libelleCourt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.structure.libelleLong')" for="structure-libelleLong"
              >Libelle Long</label
            >
            <input
              type="text"
              class="form-control"
              name="libelleLong"
              id="structure-libelleLong"
              data-cy="libelleLong"
              :class="{ valid: !$v.structure.libelleLong.$invalid, invalid: $v.structure.libelleLong.$invalid }"
              v-model="$v.structure.libelleLong.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.structure.ordre')" for="structure-ordre">Ordre</label>
            <input
              type="number"
              class="form-control"
              name="ordre"
              id="structure-ordre"
              data-cy="ordre"
              :class="{ valid: !$v.structure.ordre.$invalid, invalid: $v.structure.ordre.$invalid }"
              v-model.number="$v.structure.ordre.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('clarisseApp.structure.publicationSurInternet')"
              for="structure-publicationSurInternet"
              >Publication Sur Internet</label
            >
            <input
              type="text"
              class="form-control"
              name="publicationSurInternet"
              id="structure-publicationSurInternet"
              data-cy="publicationSurInternet"
              :class="{ valid: !$v.structure.publicationSurInternet.$invalid, invalid: $v.structure.publicationSurInternet.$invalid }"
              v-model="$v.structure.publicationSurInternet.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.structure.typeStructure')" for="structure-typeStructure"
              >Type Structure</label
            >
            <select
              class="form-control"
              id="structure-typeStructure"
              data-cy="typeStructure"
              name="typeStructure"
              v-model="structure.typeStructure"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  structure.typeStructure && typeStructureOption.id === structure.typeStructure.id
                    ? structure.typeStructure
                    : typeStructureOption
                "
                v-for="typeStructureOption in typeStructures"
                :key="typeStructureOption.id"
              >
                {{ typeStructureOption.id }}
              </option>
            </select>
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
            :disabled="$v.structure.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./structure-update.component.ts"></script>
