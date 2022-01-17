<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="appartenance">
        <h2 class="jh-entity-heading" data-cy="appartenanceDetailsHeading">
          <span v-text="$t('clarisseApp.appartenance.detail.title')">Appartenance</span> {{ appartenance.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('clarisseApp.appartenance.matricule')">Matricule</span>
          </dt>
          <dd>
            <span>{{ appartenance.matricule }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.appartenance.dateDebut')">Date Debut</span>
          </dt>
          <dd>
            <span>{{ appartenance.dateDebut }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.appartenance.dateFin')">Date Fin</span>
          </dt>
          <dd>
            <span>{{ appartenance.dateFin }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.appartenance.dateElection')">Date Election</span>
          </dt>
          <dd>
            <span>{{ appartenance.dateElection }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.appartenance.observation')">Observation</span>
          </dt>
          <dd>
            <span>{{ appartenance.observation }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.appartenance.departement')">Departement</span>
          </dt>
          <dd>
            <span>{{ appartenance.departement }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.appartenance.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ appartenance.libelle }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.appartenance.article')">Article</span>
          </dt>
          <dd>
            <span>{{ appartenance.article }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.appartenance.motifDeDebut')">Motif De Debut</span>
          </dt>
          <dd>
            <span>{{ appartenance.motifDeDebut }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.appartenance.commentaireDeChangement')">Commentaire De Changement</span>
          </dt>
          <dd>
            <span>{{ appartenance.commentaireDeChangement }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.appartenance.typeAppartenance')">Type Appartenance</span>
          </dt>
          <dd>
            <div v-if="appartenance.typeAppartenance">
              <router-link :to="{ name: 'TypeAppartenanceView', params: { typeAppartenanceId: appartenance.typeAppartenance.id } }">{{
                appartenance.typeAppartenance.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.appartenance.structure')">Structure</span>
          </dt>
          <dd>
            <div v-if="appartenance.structure">
              <router-link :to="{ name: 'StructureView', params: { structureId: appartenance.structure.id } }">{{
                appartenance.structure.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.appartenance.personne')">Personne</span>
          </dt>
          <dd>
            <div v-if="appartenance.personne">
              <router-link :to="{ name: 'PersonneView', params: { personneId: appartenance.personne.id } }">{{
                appartenance.personne.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="appartenance.id"
          :to="{ name: 'AppartenanceEdit', params: { appartenanceId: appartenance.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./appartenance-details.component.ts"></script>
