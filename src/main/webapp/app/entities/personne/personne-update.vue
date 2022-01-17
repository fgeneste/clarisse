<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="clarisseApp.personne.home.createOrEditLabel"
          data-cy="PersonneCreateUpdateHeading"
          v-text="$t('clarisseApp.personne.home.createOrEditLabel')"
        >
          Create or edit a Personne
        </h2>
        <div>
          <div class="form-group" v-if="personne.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="personne.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.personne.matricule')" for="personne-matricule">Matricule</label>
            <input
              type="text"
              class="form-control"
              name="matricule"
              id="personne-matricule"
              data-cy="matricule"
              :class="{ valid: !$v.personne.matricule.$invalid, invalid: $v.personne.matricule.$invalid }"
              v-model="$v.personne.matricule.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.personne.dateDeNaissance')" for="personne-dateDeNaissance"
              >Date De Naissance</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="personne-dateDeNaissance"
                  v-model="$v.personne.dateDeNaissance.$model"
                  name="dateDeNaissance"
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
                id="personne-dateDeNaissance"
                data-cy="dateDeNaissance"
                type="text"
                class="form-control"
                name="dateDeNaissance"
                :class="{ valid: !$v.personne.dateDeNaissance.$invalid, invalid: $v.personne.dateDeNaissance.$invalid }"
                v-model="$v.personne.dateDeNaissance.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.personne.lieuDeNaissance')" for="personne-lieuDeNaissance"
              >Lieu De Naissance</label
            >
            <input
              type="text"
              class="form-control"
              name="lieuDeNaissance"
              id="personne-lieuDeNaissance"
              data-cy="lieuDeNaissance"
              :class="{ valid: !$v.personne.lieuDeNaissance.$invalid, invalid: $v.personne.lieuDeNaissance.$invalid }"
              v-model="$v.personne.lieuDeNaissance.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('clarisseApp.personne.departementDeNaissance')"
              for="personne-departementDeNaissance"
              >Departement De Naissance</label
            >
            <input
              type="number"
              class="form-control"
              name="departementDeNaissance"
              id="personne-departementDeNaissance"
              data-cy="departementDeNaissance"
              :class="{ valid: !$v.personne.departementDeNaissance.$invalid, invalid: $v.personne.departementDeNaissance.$invalid }"
              v-model.number="$v.personne.departementDeNaissance.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.personne.dateDeDeces')" for="personne-dateDeDeces"
              >Date De Deces</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="personne-dateDeDeces"
                  v-model="$v.personne.dateDeDeces.$model"
                  name="dateDeDeces"
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
                id="personne-dateDeDeces"
                data-cy="dateDeDeces"
                type="text"
                class="form-control"
                name="dateDeDeces"
                :class="{ valid: !$v.personne.dateDeDeces.$invalid, invalid: $v.personne.dateDeDeces.$invalid }"
                v-model="$v.personne.dateDeDeces.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.personne.lieuDeDeces')" for="personne-lieuDeDeces"
              >Lieu De Deces</label
            >
            <input
              type="text"
              class="form-control"
              name="lieuDeDeces"
              id="personne-lieuDeDeces"
              data-cy="lieuDeDeces"
              :class="{ valid: !$v.personne.lieuDeDeces.$invalid, invalid: $v.personne.lieuDeDeces.$invalid }"
              v-model="$v.personne.lieuDeDeces.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.personne.departementDeDeces')" for="personne-departementDeDeces"
              >Departement De Deces</label
            >
            <input
              type="number"
              class="form-control"
              name="departementDeDeces"
              id="personne-departementDeDeces"
              data-cy="departementDeDeces"
              :class="{ valid: !$v.personne.departementDeDeces.$invalid, invalid: $v.personne.departementDeDeces.$invalid }"
              v-model.number="$v.personne.departementDeDeces.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.personne.profession')" for="personne-profession">Profession</label>
            <input
              type="text"
              class="form-control"
              name="profession"
              id="personne-profession"
              data-cy="profession"
              :class="{ valid: !$v.personne.profession.$invalid, invalid: $v.personne.profession.$invalid }"
              v-model="$v.personne.profession.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.personne.diplome')" for="personne-diplome">Diplome</label>
            <input
              type="text"
              class="form-control"
              name="diplome"
              id="personne-diplome"
              data-cy="diplome"
              :class="{ valid: !$v.personne.diplome.$invalid, invalid: $v.personne.diplome.$invalid }"
              v-model="$v.personne.diplome.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.personne.decoration')" for="personne-decoration">Decoration</label>
            <input
              type="text"
              class="form-control"
              name="decoration"
              id="personne-decoration"
              data-cy="decoration"
              :class="{ valid: !$v.personne.decoration.$invalid, invalid: $v.personne.decoration.$invalid }"
              v-model="$v.personne.decoration.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.personne.rangProtocolaire')" for="personne-rangProtocolaire"
              >Rang Protocolaire</label
            >
            <input
              type="number"
              class="form-control"
              name="rangProtocolaire"
              id="personne-rangProtocolaire"
              data-cy="rangProtocolaire"
              :class="{ valid: !$v.personne.rangProtocolaire.$invalid, invalid: $v.personne.rangProtocolaire.$invalid }"
              v-model.number="$v.personne.rangProtocolaire.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.personne.cspInsee')" for="personne-cspInsee">Csp Insee</label>
            <input
              type="text"
              class="form-control"
              name="cspInsee"
              id="personne-cspInsee"
              data-cy="cspInsee"
              :class="{ valid: !$v.personne.cspInsee.$invalid, invalid: $v.personne.cspInsee.$invalid }"
              v-model="$v.personne.cspInsee.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('clarisseApp.personne.nuancePolitique')" for="personne-nuancePolitique"
              >Nuance Politique</label
            >
            <select
              class="form-control"
              id="personne-nuancePolitique"
              data-cy="nuancePolitique"
              name="nuancePolitique"
              v-model="personne.nuancePolitique"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  personne.nuancePolitique && nuancePolitiqueOption.id === personne.nuancePolitique.id
                    ? personne.nuancePolitique
                    : nuancePolitiqueOption
                "
                v-for="nuancePolitiqueOption in nuancePolitiques"
                :key="nuancePolitiqueOption.id"
              >
                {{ nuancePolitiqueOption.id }}
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
            :disabled="$v.personne.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./personne-update.component.ts"></script>
