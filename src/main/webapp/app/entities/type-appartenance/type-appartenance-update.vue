<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="clarisseApp.typeAppartenance.home.createOrEditLabel"
          data-cy="TypeAppartenanceCreateUpdateHeading"
          v-text="$t('clarisseApp.typeAppartenance.home.createOrEditLabel')"
        >
          Create or edit a TypeAppartenance
        </h2>
        <div>
          <div class="form-group" v-if="typeAppartenance.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="typeAppartenance.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('clarisseApp.typeAppartenance.typeAppartenance')"
              for="type-appartenance-typeAppartenance"
              >Type Appartenance</label
            >
            <input
              type="text"
              class="form-control"
              name="typeAppartenance"
              id="type-appartenance-typeAppartenance"
              data-cy="typeAppartenance"
              :class="{ valid: !$v.typeAppartenance.typeAppartenance.$invalid, invalid: $v.typeAppartenance.typeAppartenance.$invalid }"
              v-model="$v.typeAppartenance.typeAppartenance.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.typeAppartenance.libelle')" for="type-appartenance-libelle"
              >Libelle</label
            >
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="type-appartenance-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.typeAppartenance.libelle.$invalid, invalid: $v.typeAppartenance.libelle.$invalid }"
              v-model="$v.typeAppartenance.libelle.$model"
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
            :disabled="$v.typeAppartenance.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./type-appartenance-update.component.ts"></script>
