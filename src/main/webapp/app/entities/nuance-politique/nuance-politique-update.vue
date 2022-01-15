<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="clarisseApp.nuancePolitique.home.createOrEditLabel"
          data-cy="NuancePolitiqueCreateUpdateHeading"
          v-text="$t('clarisseApp.nuancePolitique.home.createOrEditLabel')"
        >
          Create or edit a NuancePolitique
        </h2>
        <div>
          <div class="form-group" v-if="nuancePolitique.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="nuancePolitique.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('clarisseApp.nuancePolitique.codeNuancePolitique')"
              for="nuance-politique-codeNuancePolitique"
              >Code Nuance Politique</label
            >
            <input
              type="text"
              class="form-control"
              name="codeNuancePolitique"
              id="nuance-politique-codeNuancePolitique"
              data-cy="codeNuancePolitique"
              :class="{ valid: !$v.nuancePolitique.codeNuancePolitique.$invalid, invalid: $v.nuancePolitique.codeNuancePolitique.$invalid }"
              v-model="$v.nuancePolitique.codeNuancePolitique.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.nuancePolitique.libelle')" for="nuance-politique-libelle"
              >Libelle</label
            >
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="nuance-politique-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.nuancePolitique.libelle.$invalid, invalid: $v.nuancePolitique.libelle.$invalid }"
              v-model="$v.nuancePolitique.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.nuancePolitique.personne')" for="nuance-politique-personne"
              >Personne</label
            >
            <select
              class="form-control"
              id="nuance-politique-personne"
              data-cy="personne"
              name="personne"
              v-model="nuancePolitique.personne"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  nuancePolitique.personne && personneOption.id === nuancePolitique.personne.id ? nuancePolitique.personne : personneOption
                "
                v-for="personneOption in personnes"
                :key="personneOption.id"
              >
                {{ personneOption.id }}
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
            :disabled="$v.nuancePolitique.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./nuance-politique-update.component.ts"></script>
