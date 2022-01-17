<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="typeAppartenance">
        <h2 class="jh-entity-heading" data-cy="typeAppartenanceDetailsHeading">
          <span v-text="$t('clarisseApp.typeAppartenance.detail.title')">TypeAppartenance</span> {{ typeAppartenance.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('clarisseApp.typeAppartenance.typeAppartenance')">Type Appartenance</span>
          </dt>
          <dd>
            <span>{{ typeAppartenance.typeAppartenance }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.typeAppartenance.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ typeAppartenance.libelle }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="typeAppartenance.id"
          :to="{ name: 'TypeAppartenanceEdit', params: { typeAppartenanceId: typeAppartenance.id } }"
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

<script lang="ts" src="./type-appartenance-details.component.ts"></script>
