<template>
  <div>
    <b-dropdown-item to="/personne">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.personne')">Personne</span>
    </b-dropdown-item>
    <b-dropdown-item to="/nuance-politique">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.nuancePolitique')">Nuance Politique</span>
    </b-dropdown-item>
    <b-dropdown-item to="/noms">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.noms')">Noms</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
