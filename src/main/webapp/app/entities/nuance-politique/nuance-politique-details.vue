<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="nuancePolitique">
        <h2 class="jh-entity-heading" data-cy="nuancePolitiqueDetailsHeading">
          <span v-text="$t('clarisseApp.nuancePolitique.detail.title')">NuancePolitique</span> {{ nuancePolitique.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('clarisseApp.nuancePolitique.codeNuancePolitique')">Code Nuance Politique</span>
          </dt>
          <dd>
            <span>{{ nuancePolitique.codeNuancePolitique }}</span>
          </dd>
          <dt>
            <span v-text="$t('clarisseApp.nuancePolitique.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ nuancePolitique.libelle }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="nuancePolitique.id"
          :to="{ name: 'NuancePolitiqueEdit', params: { nuancePolitiqueId: nuancePolitique.id } }"
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

<script lang="ts" src="./nuance-politique-details.component.ts"></script>
