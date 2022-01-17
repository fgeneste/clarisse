<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="clarisseApp.appartenance.home.createOrEditLabel"
          data-cy="AppartenanceCreateUpdateHeading"
          v-text="$t('clarisseApp.appartenance.home.createOrEditLabel')"
        >
          Create or edit a Appartenance
        </h2>
        <div>
          <div class="form-group" v-if="appartenance.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="appartenance.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.appartenance.matricule')" for="appartenance-matricule"
              >Matricule</label
            >
            <input
              type="text"
              class="form-control"
              name="matricule"
              id="appartenance-matricule"
              data-cy="matricule"
              :class="{ valid: !$v.appartenance.matricule.$invalid, invalid: $v.appartenance.matricule.$invalid }"
              v-model="$v.appartenance.matricule.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.appartenance.dateDebut')" for="appartenance-dateDebut"
              >Date Debut</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="appartenance-dateDebut"
                  v-model="$v.appartenance.dateDebut.$model"
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
                id="appartenance-dateDebut"
                data-cy="dateDebut"
                type="text"
                class="form-control"
                name="dateDebut"
                :class="{ valid: !$v.appartenance.dateDebut.$invalid, invalid: $v.appartenance.dateDebut.$invalid }"
                v-model="$v.appartenance.dateDebut.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.appartenance.dateFin')" for="appartenance-dateFin">Date Fin</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="appartenance-dateFin"
                  v-model="$v.appartenance.dateFin.$model"
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
                id="appartenance-dateFin"
                data-cy="dateFin"
                type="text"
                class="form-control"
                name="dateFin"
                :class="{ valid: !$v.appartenance.dateFin.$invalid, invalid: $v.appartenance.dateFin.$invalid }"
                v-model="$v.appartenance.dateFin.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.appartenance.dateElection')" for="appartenance-dateElection"
              >Date Election</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="appartenance-dateElection"
                  v-model="$v.appartenance.dateElection.$model"
                  name="dateElection"
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
                id="appartenance-dateElection"
                data-cy="dateElection"
                type="text"
                class="form-control"
                name="dateElection"
                :class="{ valid: !$v.appartenance.dateElection.$invalid, invalid: $v.appartenance.dateElection.$invalid }"
                v-model="$v.appartenance.dateElection.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.appartenance.observation')" for="appartenance-observation"
              >Observation</label
            >
            <input
              type="text"
              class="form-control"
              name="observation"
              id="appartenance-observation"
              data-cy="observation"
              :class="{ valid: !$v.appartenance.observation.$invalid, invalid: $v.appartenance.observation.$invalid }"
              v-model="$v.appartenance.observation.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.appartenance.departement')" for="appartenance-departement"
              >Departement</label
            >
            <input
              type="number"
              class="form-control"
              name="departement"
              id="appartenance-departement"
              data-cy="departement"
              :class="{ valid: !$v.appartenance.departement.$invalid, invalid: $v.appartenance.departement.$invalid }"
              v-model.number="$v.appartenance.departement.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.appartenance.libelle')" for="appartenance-libelle">Libelle</label>
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="appartenance-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.appartenance.libelle.$invalid, invalid: $v.appartenance.libelle.$invalid }"
              v-model="$v.appartenance.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.appartenance.article')" for="appartenance-article">Article</label>
            <input
              type="text"
              class="form-control"
              name="article"
              id="appartenance-article"
              data-cy="article"
              :class="{ valid: !$v.appartenance.article.$invalid, invalid: $v.appartenance.article.$invalid }"
              v-model="$v.appartenance.article.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.appartenance.motifDeDebut')" for="appartenance-motifDeDebut"
              >Motif De Debut</label
            >
            <input
              type="text"
              class="form-control"
              name="motifDeDebut"
              id="appartenance-motifDeDebut"
              data-cy="motifDeDebut"
              :class="{ valid: !$v.appartenance.motifDeDebut.$invalid, invalid: $v.appartenance.motifDeDebut.$invalid }"
              v-model="$v.appartenance.motifDeDebut.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('clarisseApp.appartenance.commentaireDeChangement')"
              for="appartenance-commentaireDeChangement"
              >Commentaire De Changement</label
            >
            <input
              type="text"
              class="form-control"
              name="commentaireDeChangement"
              id="appartenance-commentaireDeChangement"
              data-cy="commentaireDeChangement"
              :class="{
                valid: !$v.appartenance.commentaireDeChangement.$invalid,
                invalid: $v.appartenance.commentaireDeChangement.$invalid,
              }"
              v-model="$v.appartenance.commentaireDeChangement.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.appartenance.typeAppartenance')" for="appartenance-typeAppartenance"
              >Type Appartenance</label
            >
            <select
              class="form-control"
              id="appartenance-typeAppartenance"
              data-cy="typeAppartenance"
              name="typeAppartenance"
              v-model="appartenance.typeAppartenance"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  appartenance.typeAppartenance && typeAppartenanceOption.id === appartenance.typeAppartenance.id
                    ? appartenance.typeAppartenance
                    : typeAppartenanceOption
                "
                v-for="typeAppartenanceOption in typeAppartenances"
                :key="typeAppartenanceOption.id"
              >
                {{ typeAppartenanceOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.appartenance.structure')" for="appartenance-structure"
              >Structure</label
            >
            <select class="form-control" id="appartenance-structure" data-cy="structure" name="structure" v-model="appartenance.structure">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  appartenance.structure && structureOption.id === appartenance.structure.id ? appartenance.structure : structureOption
                "
                v-for="structureOption in structures"
                :key="structureOption.id"
              >
                {{ structureOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.appartenance.personne')" for="appartenance-personne">Personne</label>
            <select class="form-control" id="appartenance-personne" data-cy="personne" name="personne" v-model="appartenance.personne">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  appartenance.personne && personneOption.id === appartenance.personne.id ? appartenance.personne : personneOption
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
            :disabled="$v.appartenance.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./appartenance-update.component.ts"></script>
