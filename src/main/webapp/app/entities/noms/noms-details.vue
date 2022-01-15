<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="noms">
        <h2 class="jh-entity-heading" data-cy="nomsDetailsHeading">
          <span v-text="$t('clarisseApp.noms.detail.title')">Noms</span> {{ noms.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('clarisseApp.noms.qualite')">Qualite</span>
          </dt>
          <dd>
            <span>{{ noms.qualite }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.noms.nomUsuel')">Nom Usuel</span>
          </dt>
          <dd>
            <span>{{ noms.nomUsuel }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.noms.prenomUsuel')">Prenom Usuel</span>
          </dt>
          <dd>
            <span>{{ noms.prenomUsuel }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.noms.nomPatronymique')">Nom Patronymique</span>
          </dt>
          <dd>
            <span>{{ noms.nomPatronymique }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.noms.prenomCivil')">Prenom Civil</span>
          </dt>
          <dd>
            <span>{{ noms.prenomCivil }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.noms.nomMarital')">Nom Marital</span>
          </dt>
          <dd>
            <span>{{ noms.nomMarital }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.noms.nomDistinctif')">Nom Distinctif</span>
          </dt>
          <dd>
            <span>{{ noms.nomDistinctif }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.noms.nomMajuscule')">Nom Majuscule</span>
          </dt>
          <dd>
            <span>{{ noms.nomMajuscule }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.noms.nomTechnique')">Nom Technique</span>
          </dt>
          <dd>
            <span>{{ noms.nomTechnique }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.noms.feminisation')">Feminisation</span>
          </dt>
          <dd>
            <span>{{ noms.feminisation }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.noms.dateDebut')">Date Debut</span>
          </dt>
          <dd>
            <span>{{ noms.dateDebut }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.noms.dateFin')">Date Fin</span>
          </dt>
          <dd>
            <span>{{ noms.dateFin }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.noms.personne')">Personne</span>
          </dt>
          <dd>
            <div v-if="noms.personne">
              <router-link :to="{ name: 'PersonneView', params: { personneId: noms.personne.id } }">{{ noms.personne.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="noms.id" :to="{ name: 'NomsEdit', params: { nomsId: noms.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./noms-details.component.ts"></script>
