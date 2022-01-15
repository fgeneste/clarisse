<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="clarisseApp.noms.home.createOrEditLabel"
          data-cy="NomsCreateUpdateHeading"
          v-text="$t('clarisseApp.noms.home.createOrEditLabel')"
        >
          Create or edit a Noms
        </h2>
        <div>
          <div class="form-group" v-if="noms.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="noms.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.noms.qualite')" for="noms-qualite">Qualite</label>
            <input
              type="text"
              class="form-control"
              name="qualite"
              id="noms-qualite"
              data-cy="qualite"
              :class="{ valid: !$v.noms.qualite.$invalid, invalid: $v.noms.qualite.$invalid }"
              v-model="$v.noms.qualite.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.noms.nomUsuel')" for="noms-nomUsuel">Nom Usuel</label>
            <input
              type="text"
              class="form-control"
              name="nomUsuel"
              id="noms-nomUsuel"
              data-cy="nomUsuel"
              :class="{ valid: !$v.noms.nomUsuel.$invalid, invalid: $v.noms.nomUsuel.$invalid }"
              v-model="$v.noms.nomUsuel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.noms.prenomUsuel')" for="noms-prenomUsuel">Prenom Usuel</label>
            <input
              type="text"
              class="form-control"
              name="prenomUsuel"
              id="noms-prenomUsuel"
              data-cy="prenomUsuel"
              :class="{ valid: !$v.noms.prenomUsuel.$invalid, invalid: $v.noms.prenomUsuel.$invalid }"
              v-model="$v.noms.prenomUsuel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.noms.nomPatronymique')" for="noms-nomPatronymique"
              >Nom Patronymique</label
            >
            <input
              type="text"
              class="form-control"
              name="nomPatronymique"
              id="noms-nomPatronymique"
              data-cy="nomPatronymique"
              :class="{ valid: !$v.noms.nomPatronymique.$invalid, invalid: $v.noms.nomPatronymique.$invalid }"
              v-model="$v.noms.nomPatronymique.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.noms.prenomCivil')" for="noms-prenomCivil">Prenom Civil</label>
            <input
              type="text"
              class="form-control"
              name="prenomCivil"
              id="noms-prenomCivil"
              data-cy="prenomCivil"
              :class="{ valid: !$v.noms.prenomCivil.$invalid, invalid: $v.noms.prenomCivil.$invalid }"
              v-model="$v.noms.prenomCivil.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.noms.nomMarital')" for="noms-nomMarital">Nom Marital</label>
            <input
              type="text"
              class="form-control"
              name="nomMarital"
              id="noms-nomMarital"
              data-cy="nomMarital"
              :class="{ valid: !$v.noms.nomMarital.$invalid, invalid: $v.noms.nomMarital.$invalid }"
              v-model="$v.noms.nomMarital.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.noms.nomDistinctif')" for="noms-nomDistinctif">Nom Distinctif</label>
            <input
              type="text"
              class="form-control"
              name="nomDistinctif"
              id="noms-nomDistinctif"
              data-cy="nomDistinctif"
              :class="{ valid: !$v.noms.nomDistinctif.$invalid, invalid: $v.noms.nomDistinctif.$invalid }"
              v-model="$v.noms.nomDistinctif.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.noms.nomMajuscule')" for="noms-nomMajuscule">Nom Majuscule</label>
            <input
              type="text"
              class="form-control"
              name="nomMajuscule"
              id="noms-nomMajuscule"
              data-cy="nomMajuscule"
              :class="{ valid: !$v.noms.nomMajuscule.$invalid, invalid: $v.noms.nomMajuscule.$invalid }"
              v-model="$v.noms.nomMajuscule.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.noms.nomTechnique')" for="noms-nomTechnique">Nom Technique</label>
            <input
              type="text"
              class="form-control"
              name="nomTechnique"
              id="noms-nomTechnique"
              data-cy="nomTechnique"
              :class="{ valid: !$v.noms.nomTechnique.$invalid, invalid: $v.noms.nomTechnique.$invalid }"
              v-model="$v.noms.nomTechnique.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.noms.feminisation')" for="noms-feminisation">Feminisation</label>
            <input
              type="text"
              class="form-control"
              name="feminisation"
              id="noms-feminisation"
              data-cy="feminisation"
              :class="{ valid: !$v.noms.feminisation.$invalid, invalid: $v.noms.feminisation.$invalid }"
              v-model="$v.noms.feminisation.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.noms.dateDebut')" for="noms-dateDebut">Date Debut</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="noms-dateDebut"
                  v-model="$v.noms.dateDebut.$model"
                  name="dateDebut"
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
                id="noms-dateDebut"
                data-cy="dateDebut"
                type="text"
                class="form-control"
                name="dateDebut"
                :class="{ valid: !$v.noms.dateDebut.$invalid, invalid: $v.noms.dateDebut.$invalid }"
                v-model="$v.noms.dateDebut.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.noms.dateFin')" for="noms-dateFin">Date Fin</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="noms-dateFin"
                  v-model="$v.noms.dateFin.$model"
                  name="dateFin"
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
                id="noms-dateFin"
                data-cy="dateFin"
                type="text"
                class="form-control"
                name="dateFin"
                :class="{ valid: !$v.noms.dateFin.$invalid, invalid: $v.noms.dateFin.$invalid }"
                v-model="$v.noms.dateFin.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.noms.personne')" for="noms-personne">Personne</label>
            <select class="form-control" id="noms-personne" data-cy="personne" name="personne" v-model="noms.personne">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="noms.personne && personneOption.id === noms.personne.id ? noms.personne : personneOption"
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
            :disabled="$v.noms.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./noms-update.component.ts"></script>
