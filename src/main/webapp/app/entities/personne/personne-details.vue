<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="personne">
        <h2 class="jh-entity-heading" data-cy="personneDetailsHeading">
          <span v-text="$t('clarisseApp.personne.detail.title')">Personne</span> {{ personne.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('clarisseApp.personne.matricule')">Matricule</span>
          </dt>
          <dd>
            <span>{{ personne.matricule }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.personne.dateDeNaissance')">Date De Naissance</span>
          </dt>
          <dd>
            <span>{{ personne.dateDeNaissance }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.personne.lieuDeNaissance')">Lieu De Naissance</span>
          </dt>
          <dd>
            <span>{{ personne.lieuDeNaissance }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.personne.departementDeNaissance')">Departement De Naissance</span>
          </dt>
          <dd>
            <span>{{ personne.departementDeNaissance }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.personne.dateDeDeces')">Date De Deces</span>
          </dt>
          <dd>
            <span>{{ personne.dateDeDeces }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.personne.lieuDeDeces')">Lieu De Deces</span>
          </dt>
          <dd>
            <span>{{ personne.lieuDeDeces }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.personne.departementDeDeces')">Departement De Deces</span>
          </dt>
          <dd>
            <span>{{ personne.departementDeDeces }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.personne.profession')">Profession</span>
          </dt>
          <dd>
            <span>{{ personne.profession }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.personne.diplome')">Diplome</span>
          </dt>
          <dd>
            <span>{{ personne.diplome }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.personne.decoration')">Decoration</span>
          </dt>
          <dd>
            <span>{{ personne.decoration }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.personne.rangProtocolaire')">Rang Protocolaire</span>
          </dt>
          <dd>
            <span>{{ personne.rangProtocolaire }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.personne.cspInsee')">Csp Insee</span>
          </dt>
          <dd>
            <span>{{ personne.cspInsee }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.personne.nuancePolitique')">Nuance Politique</span>
          </dt>
          <dd>
            <div v-if="personne.nuancePolitique">
              <router-link :to="{ name: 'NuancePolitiqueView', params: { nuancePolitiqueId: personne.nuancePolitique.id } }">{{
                personne.nuancePolitique.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="personne.id" :to="{ name: 'PersonneEdit', params: { personneId: personne.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./personne-details.component.ts"></script>
