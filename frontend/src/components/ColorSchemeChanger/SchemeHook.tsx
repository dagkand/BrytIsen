import { useEffect, useMemo } from 'react';
import { useMediaQuery } from "react-responsive";
import createPersistedState from "@plq/use-persisted-state";
import storage from '@plq/use-persisted-state/lib/storages/local-storage'

const [useColorSchemeState] = createPersistedState("scheme", storage);

const useScheme = () => {
    const systemPrefersDark = useMediaQuery(
      {
        query: "(prefers-color-scheme: dark)",
      },
      undefined
    );

    const [isDarkMode, setDarkMode] = useColorSchemeState('isDarkMode', false);
    
    const val = useMemo(() => 
    (isDarkMode === undefined ? !!systemPrefersDark : isDarkMode),
    [isDarkMode, systemPrefersDark]
    );

    useEffect(() => {
        if (val) {
            document.body.classList.add("dark");
        } else {
            document.body.classList.remove("dark");
        }
    }, [val]);

    return {
      isDarkMode: val,
      setDarkMode,
    };
};

export default useScheme;